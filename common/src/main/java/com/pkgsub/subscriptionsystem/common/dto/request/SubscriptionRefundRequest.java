package com.pkgsub.subscriptionsystem.common.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionRefundRequest {
    @NotNull(message = "User id cannot be null")
    private Long userId;

    @NotNull(message = "Subscription id cannot be null")
    private String subscriptionId;

    @NotNull(message = "Package id cannot be null")
    private String packageId;
}
