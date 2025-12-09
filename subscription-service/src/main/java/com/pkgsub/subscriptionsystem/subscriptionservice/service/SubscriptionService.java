package com.pkgsub.subscriptionsystem.subscriptionservice.service;

import com.pkgsub.subscriptionsystem.common.dto.request.SubscriptionRefundRequest;
import com.pkgsub.subscriptionsystem.common.dto.request.SubscriptionCreditRequest;
import com.pkgsub.subscriptionsystem.common.dto.request.UserBalanceCreditRequest;
import com.pkgsub.subscriptionsystem.common.dto.response.SubscriptionDto;
import com.pkgsub.subscriptionsystem.common.exceptions.RefundException;
import com.pkgsub.subscriptionsystem.common.exceptions.SubscriptionException;

import java.util.List;

public interface SubscriptionService {
    List<SubscriptionDto> getSubscriptions(String userId);
    SubscriptionDto subscribe(SubscriptionCreditRequest subscriptionCreditRequest) throws SubscriptionException;
    void refundSubscription(SubscriptionRefundRequest subscriptionRefundRequest) throws RefundException;
//    void processRefundEffects(Long packageId); // called after refund to trigger wishlist handling
}