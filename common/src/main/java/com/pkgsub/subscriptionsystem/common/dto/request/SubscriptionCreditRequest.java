package com.pkgsub.subscriptionsystem.common.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionCreditRequest {
    @NotEmpty(message = "User id cannot be empty")
    String userId;

    @NotEmpty(message = "Package id cannot be empty")
    String packageId;
}