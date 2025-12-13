package com.pkgsub.subscriptionsystem.billingservice.service;

import com.pkgsub.subscriptionsystem.common.dto.request.TopUpRequest;
import com.pkgsub.subscriptionsystem.common.dto.request.UserBalanceCreditRequest;
import com.pkgsub.subscriptionsystem.common.dto.request.UserBalanceDebitRequest;

public interface BillingService {

    void topUp(TopUpRequest request);

    void debit(UserBalanceDebitRequest userBalanceDebitRequest);

    void credit(UserBalanceCreditRequest userBalanceCreditRequest);
}