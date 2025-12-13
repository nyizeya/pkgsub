package com.pkgsub.subscriptionsystem.billingservice.web.rest;

import com.pkgsub.subscriptionsystem.common.dto.request.TopUpRequest;
import com.pkgsub.subscriptionsystem.common.dto.request.UserBalanceCreditRequest;
import com.pkgsub.subscriptionsystem.common.dto.request.UserBalanceDebitRequest;
import com.pkgsub.subscriptionsystem.billingservice.service.BillingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/billing")
public class BillingResource {

    private final BillingService billingService;

    @PostMapping("/topUp")
    public ResponseEntity<Void> topUp(@Valid @RequestBody TopUpRequest request) {
        billingService.topUp(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/debit/fund")
    public ResponseEntity<Void> debitFunds(@Valid @RequestBody UserBalanceDebitRequest request) {
        billingService.debit(request);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/credit/fund")
    public ResponseEntity<Void> creditFunds(@Valid @RequestBody UserBalanceCreditRequest request) {
        billingService.credit(request);
        return ResponseEntity.noContent().build();
    }
}