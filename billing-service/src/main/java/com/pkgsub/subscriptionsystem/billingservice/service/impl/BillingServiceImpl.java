package com.pkgsub.subscriptionsystem.billingservice.service.impl;

import com.pkgsub.subscriptionsystem.common.dto.request.TopUpRequest;
import com.pkgsub.subscriptionsystem.common.dto.request.UserBalanceCreditRequest;
import com.pkgsub.subscriptionsystem.common.dto.request.UserBalanceDebitRequest;
import com.pkgsub.subscriptionsystem.common.exceptions.EntityNotFoundException;
import com.pkgsub.subscriptionsystem.common.exceptions.TransactionValidationException;
import com.pkgsub.subscriptionsystem.billingservice.entity.User;
import com.pkgsub.subscriptionsystem.billingservice.repository.UserRepository;
import com.pkgsub.subscriptionsystem.billingservice.service.BillingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
public class BillingServiceImpl implements BillingService {
    private final UserRepository userRepository;

    @Transactional
    @Override
    public void topUp(TopUpRequest request) {
        if (request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new TransactionValidationException(HttpStatus.BAD_REQUEST, "Top up amount must be greater than zero");
        }

        if (request.getAmount().compareTo(new BigDecimal(10_000)) > 0) {
            throw new TransactionValidationException(HttpStatus.BAD_REQUEST, "Ain't it too much? We know you're not that rich.");
        }

        User user = findById(request.getUserId());
        user.setBalance(user.getBalance().add(request.getAmount()));
        userRepository.save(user);
    }

    @Transactional
    @Override
    public void debit(UserBalanceDebitRequest userBalanceDebitRequest) {
        var entity = findById(userBalanceDebitRequest.getUserId());

        if (entity.getBalance().compareTo(userBalanceDebitRequest.getAmount()) < 0) {
            throw new TransactionValidationException(HttpStatus.BAD_REQUEST, "User does not have enough balance.");
        }

        entity.setBalance(entity.getBalance().subtract(userBalanceDebitRequest.getAmount()));
        userRepository.save(entity);
    }

    @Transactional
    @Override
    public void credit(UserBalanceCreditRequest userBalanceCreditRequest) {
        if (userBalanceCreditRequest.getAmount().compareTo(BigDecimal.ZERO) < 0) {
            throw new TransactionValidationException(HttpStatus.BAD_REQUEST, "Invalid tranaction amount for credit.");
        }

        var entity = findById(userBalanceCreditRequest.getUserId());
        entity.setBalance(entity.getBalance().add(userBalanceCreditRequest.getAmount()));
        userRepository.save(entity);
    }

    private User findById(String id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User with id [%s] is not found".formatted(id)));
    }
}
