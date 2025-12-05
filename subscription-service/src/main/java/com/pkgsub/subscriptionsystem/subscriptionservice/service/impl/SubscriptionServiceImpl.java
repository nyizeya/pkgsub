package com.pkgsub.subscriptionsystem.subscriptionservice.service.impl;

import com.pkgsub.subscriptionsystem.common.dto.request.*;
import com.pkgsub.subscriptionsystem.common.dto.response.PackageDto;
import com.pkgsub.subscriptionsystem.common.dto.response.SubscriptionDto;
import com.pkgsub.subscriptionsystem.common.enumerations.SubscriptionStatus;
import com.pkgsub.subscriptionsystem.common.exceptions.EntityNotFoundException;
import com.pkgsub.subscriptionsystem.common.exceptions.RefundException;
import com.pkgsub.subscriptionsystem.common.exceptions.SubscriptionException;
import com.pkgsub.subscriptionsystem.subscriptionservice.client.PackageClient;
import com.pkgsub.subscriptionsystem.subscriptionservice.client.UserClient;
import com.pkgsub.subscriptionsystem.subscriptionservice.entity.SubscriptionEntity;
import com.pkgsub.subscriptionsystem.subscriptionservice.repository.SubscriptionRepository;
import com.pkgsub.subscriptionsystem.subscriptionservice.service.SubscriptionService;
import com.pkgsub.subscriptionsystem.subscriptionservice.web.mapper.SubscriptionMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {
    private final UserClient userClient;
    private final PackageClient packageClient;
    private final SubscriptionMapper subscriptionMapper;
    private final SubscriptionRepository subscriptionRepository;

    @Transactional
    @Override
    public SubscriptionDto subscribe(SubscriptionCreditRequest subscriptionCreditRequest) throws SubscriptionException {
        try {
            PackageDto packageDto = packageClient.getPackage(subscriptionCreditRequest.getPackageId()).getData();
            validateSubscriptionAvailability(packageDto);
            validateSubscriptionWindow(packageDto);

            userClient.debitFunds(new UserBalanceDebitRequest(subscriptionCreditRequest.getUserId(), packageDto.getPrice()));
            packageClient.updatePackageSubscriberCount(new PackageSubscriberCountUpdateRequest(packageDto.getId(), packageDto.getPermittedCount() - 1));

            SubscriptionEntity subscriptionEntity = SubscriptionEntity.builder()
                    .packageId(packageDto.getId())
                    .userId(subscriptionCreditRequest.getUserId())
                    .amount(packageDto.getPrice())
                    .createdAt(LocalDate.now())
                    .status(SubscriptionStatus.ACTIVE)
                    .build();

            return subscriptionMapper.toDTO(subscriptionRepository.save(subscriptionEntity));
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    @Transactional
    @Override
    public void refundSubscription(SubscriptionRefundRequest subscriptionRefundRequest) throws RefundException {
        try {
            var subscriptionEntity = findById(subscriptionRefundRequest.getSubscriptionId());

            if (!StringUtils.equals(subscriptionRefundRequest.getSubscriptionId(), subscriptionEntity.getId())) {
                throw new SubscriptionException(HttpStatus.BAD_REQUEST, "You cannot refund someone else's subscription");
            }

            var packageDto = packageClient.getPackage(subscriptionRefundRequest.getPackageId()).getData();

            validateRefundWindow(packageDto);

            userClient.creditFunds(new UserBalanceCreditRequest(subscriptionEntity.getUserId(), subscriptionEntity.getAmount()));
            packageClient.updatePackageSubscriberCount(new PackageSubscriberCountUpdateRequest(packageDto.getId(), packageDto.getAvailableCount() + 1));
            subscriptionRepository.delete(subscriptionEntity);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    private void validateSubscriptionAvailability(PackageDto pkg) {
        if (pkg.getAvailableCount() <= 0) {
            throw new SubscriptionException(HttpStatus.BAD_REQUEST, "Subscription slots are full");
        }
    }

    private void validateSubscriptionWindow(PackageDto pkg) {
        LocalDate today = LocalDate.now();
        if (today.isAfter(pkg.getClosedDate())) {
            throw new SubscriptionException(HttpStatus.BAD_REQUEST, "Subscription window not available");
        }
    }

    private void validateRefundWindow(PackageDto pkg) {
        LocalDate today = LocalDate.now();
        if (today.isAfter(pkg.getOpenedDate().plusWeeks(1))) {
            throw new RefundException(HttpStatus.BAD_REQUEST, "Refund period has expired");
        }
    }

    private SubscriptionEntity findById(String id) {
        return subscriptionRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(HttpStatus.NOT_FOUND, "Subscription with id [%s] is not found".formatted(id)));
    }
}
