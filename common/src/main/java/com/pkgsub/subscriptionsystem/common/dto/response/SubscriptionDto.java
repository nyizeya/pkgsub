package com.pkgsub.subscriptionsystem.common.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.pkgsub.subscriptionsystem.common.enumerations.SubscriptionStatus;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SubscriptionDto {
    String id;
    String userId;
    String packageId;
    BigDecimal amount;
    LocalDateTime createdAt;
    SubscriptionStatus status;
    LocalDateTime refundedAt;
    String externalTransactionId;
}