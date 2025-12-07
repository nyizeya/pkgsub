package com.pkgsub.subscriptionsystem.subscriptionservice.client;

import com.pkgsub.subscriptionsystem.common.dto.request.UserBalanceCreditRequest;
import com.pkgsub.subscriptionsystem.common.dto.request.UserBalanceDebitRequest;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "billing-service", path = "/api/billing")
public interface BillingClient {
    @PostMapping("/debit/fund")
    void debitFunds(@Valid @RequestBody UserBalanceDebitRequest request);

    @PostMapping("/credit/fund")
    void creditFunds(@Valid @RequestBody UserBalanceCreditRequest request);
}
