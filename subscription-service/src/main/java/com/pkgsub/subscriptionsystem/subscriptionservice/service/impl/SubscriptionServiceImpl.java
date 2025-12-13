package com.pkgsub.subscriptionsystem.subscriptionservice.service.impl;

import com.pkgsub.subscriptionsystem.common.dto.request.*;
import com.pkgsub.subscriptionsystem.common.dto.response.PackageDto;
import com.pkgsub.subscriptionsystem.common.dto.response.SubscriptionDto;
import com.pkgsub.subscriptionsystem.common.enumerations.SubscriptionStatus;
import com.pkgsub.subscriptionsystem.common.exceptions.EntityNotFoundException;
import com.pkgsub.subscriptionsystem.common.exceptions.RefundException;
import com.pkgsub.subscriptionsystem.common.exceptions.SubscriptionException;
import com.pkgsub.subscriptionsystem.subscriptionservice.client.PackageClient;
import com.pkgsub.subscriptionsystem.subscriptionservice.client.BillingClient;
import com.pkgsub.subscriptionsystem.subscriptionservice.entity.SubscriptionEntity;
import com.pkgsub.subscriptionsystem.subscriptionservice.repository.SubscriptionRepository;
import com.pkgsub.subscriptionsystem.subscriptionservice.service.RedisLockService;
import com.pkgsub.subscriptionsystem.subscriptionservice.service.SubscriptionService;
import com.pkgsub.subscriptionsystem.subscriptionservice.web.mapper.SubscriptionMapper;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {
    private final BillingClient billingClient;
    private final PackageClient packageClient;
    private final SubscriptionMapper subscriptionMapper;
    private final SubscriptionRepository subscriptionRepository;
    private final RedisLockService redisLockService;

    @Override
    public List<SubscriptionDto> getSubscriptions(String userId) {
        return subscriptionMapper.toDTOList(subscriptionRepository.findByUserId(userId));
    }

    @Transactional
    @Override
    public SubscriptionDto subscribe(SubscriptionCreditRequest request) throws SubscriptionException {
        String lockKey = "lock:package:" + request.getPackageId();
        String lockValue = UUID.randomUUID().toString();

        int maxRetries = 3;
        int retryIntervalMs = 5_000;
        boolean locked = false;


        try {
            for (int attempt = 1; attempt <= maxRetries; attempt++) {
                locked = redisLockService.acquireLock(lockKey, lockValue, 20_000);

                if (locked) {
                    log.info("Lock acquired for package {} subscription on attempt {}", request.getPackageId(), attempt);
                    break;
                }

                log.info("Subscription for package {} is locked by other user, retrying {}/{}", request.getPackageId(), attempt, maxRetries);

                Thread.sleep(retryIntervalMs);
            }


            if (!locked) {
                throw new SubscriptionException(HttpStatus.CONFLICT, "Package is busy, please try again later.");
            }

            log.info("Subscribing a package: {}", request);
            Thread.sleep(5000);
            PackageDto packageDto = packageClient.getPackage(request.getPackageId()).getData();
            validateSubscriptionAvailability(packageDto, request.getUserId());
            validateSubscriptionWindow(packageDto);

            billingClient.debitFunds(new UserBalanceDebitRequest(request.getUserId(), packageDto.getPrice()));
            packageClient.updatePackageSubscriberCount(new PackageSubscriberCountUpdateRequest(packageDto.getId(), packageDto.getPermittedCount() - 1));

            SubscriptionEntity subscriptionEntity = SubscriptionEntity.builder()
                    .packageId(packageDto.getId())
                    .packageName(packageDto.getName())
                    .userId(request.getUserId())
                    .amount(packageDto.getPrice())
                    .status(SubscriptionStatus.ACTIVE)
                    .build();

            return subscriptionMapper.toDTO(subscriptionRepository.save(subscriptionEntity));
        } catch (FeignException ex) {
            log.error("{}", ex.getMessage());
            throw new SubscriptionException(ex.status(), ex.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new SubscriptionException(500, "Thread interrupted while waiting for package lock");
        } finally {
            redisLockService.releaseLock(lockKey, lockValue);
        }
    }

    @Transactional
    @Override
    public void refundSubscription(SubscriptionRefundRequest subscriptionRefundRequest) throws RefundException {
        try {
            log.info("Refunding a package: {}", subscriptionRefundRequest);
            var subscriptionEntity = findById(subscriptionRefundRequest.getSubscriptionId());

            if (!StringUtils.equals(subscriptionRefundRequest.getSubscriptionId(), subscriptionEntity.getId())) {
                throw new SubscriptionException(HttpStatus.BAD_REQUEST, "You cannot refund someone else's subscription");
            }

            var packageDto = packageClient.getPackage(subscriptionRefundRequest.getPackageId()).getData();

            validateRefundWindow(packageDto);

            billingClient.creditFunds(new UserBalanceCreditRequest(subscriptionEntity.getUserId(), subscriptionEntity.getAmount()));
            packageClient.updatePackageSubscriberCount(new PackageSubscriberCountUpdateRequest(packageDto.getId(), packageDto.getAvailableCount() + 1));
            subscriptionRepository.delete(subscriptionEntity);
        } catch (FeignException ex) {
            log.error("{}", ex.getMessage());
            throw new RefundException(ex.status(), ex.getMessage());
        }
    }

    private void validateSubscriptionAvailability(PackageDto pkg, String userId) {
        log.info("Is there available seat for [{}] ? [{}]", pkg.getName(), pkg.getAvailableCount());

        subscriptionRepository.findByPackageIdAndUserId(pkg.getId(), userId).ifPresent(sub -> {
            throw new SubscriptionException(HttpStatus.CONFLICT, "You've already subscribed %s".formatted(pkg.getName()));
        });

        if (pkg.getAvailableCount() <= 0) {
            throw new SubscriptionException(HttpStatus.BAD_REQUEST, "Subscription slots are full");
        }
    }

    private void validateSubscriptionWindow(PackageDto pkg) {
        LocalDate today = LocalDate.now();

        log.info("Is still in subscription window for [{}]? [{}]", pkg.getName(), today.isBefore(pkg.getClosedDate()));

        if (today.isAfter(pkg.getClosedDate())) {
            throw new SubscriptionException(HttpStatus.BAD_REQUEST, "Subscription window not available");
        }
    }

    private void validateRefundWindow(PackageDto pkg) {
        LocalDate today = LocalDate.now();

        log.info("Is still in refund window for [{}]? [{}]", pkg.getName(), today.isAfter(pkg.getOpenedDate().plusWeeks(1)));

        if (today.isAfter(pkg.getOpenedDate().plusWeeks(1))) {
            throw new RefundException(HttpStatus.BAD_REQUEST, "Refund period has expired");
        }
    }

    private SubscriptionEntity findById(String id) {
        return subscriptionRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(HttpStatus.NOT_FOUND, "Subscription with id [%s] is not found".formatted(id)));
    }
}
