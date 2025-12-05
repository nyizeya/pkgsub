package com.pkgsub.subscriptionsystem.common.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionCreditRequest {
    @NotNull(message = "User id cannot be null")
    String userId;

    @NotNull(message = "Package id cannot be null")
    String packageId; // Must match PackageEntity's String ID
    // Price and status are typically determined by the service, not the client
}