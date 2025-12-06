package com.pkgsub.subscriptionsystem.common.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionRefundRequest {
    @NotEmpty(message = "User id cannot be empty")
    private String userId;

    @NotEmpty(message = "Subscription id cannot be empty")
    private String subscriptionId;

    @NotEmpty(message = "Package id cannot be empty")
    private String packageId;
}
