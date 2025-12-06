package com.pkgsub.subscriptionsystem.billingservice.service.impl;

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

@Slf4j
@Service
@RequiredArgsConstructor
public class BillingServiceImpl implements BillingService {
    private final UserRepository userRepository;


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
        var entity = findById(userBalanceCreditRequest.getUserId());
        entity.setBalance(entity.getBalance().add(userBalanceCreditRequest.getAmount()));
        userRepository.save(entity);
    }



    private User findById(String id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User with id [%s] is not found".formatted(id)));
    }
}
