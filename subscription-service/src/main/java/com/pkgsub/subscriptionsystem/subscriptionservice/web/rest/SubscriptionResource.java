package com.pkgsub.subscriptionsystem.subscriptionservice.web.rest;

import com.pkgsub.subscriptionsystem.common.dto.request.SubscriptionCreditRequest;
import com.pkgsub.subscriptionsystem.common.dto.request.SubscriptionRefundRequest;
import com.pkgsub.subscriptionsystem.common.dto.response.ApiResponse;
import com.pkgsub.subscriptionsystem.common.dto.response.SubscriptionDto;
import com.pkgsub.subscriptionsystem.subscriptionservice.service.SubscriptionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/subscriptions")
public class SubscriptionResource {

    private final SubscriptionService subscriptionService;

    @PostMapping("/subscribe")
    public ResponseEntity<ApiResponse<SubscriptionDto>> createSubscription(@Valid @RequestBody SubscriptionCreditRequest subscriptionCreditRequest) {
        return new ResponseEntity<>(ApiResponse.of(HttpStatus.CREATED, null, subscriptionService.subscribe(subscriptionCreditRequest)), HttpStatus.CREATED);
    }

    @PostMapping("/refund")
    public ResponseEntity<Void> refundSubscription(@Valid @RequestBody SubscriptionRefundRequest subscriptionRefundRequest) {
        subscriptionService.refundSubscription(subscriptionRefundRequest);
        return ResponseEntity.noContent().build();
    }

}
