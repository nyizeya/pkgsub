package com.pkgsub.subscriptionsystem.common.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PackageSubscriberCountUpdateRequest {
    @NotNull(message = "Package id cannot be null")
    private String pkgId;

    @NotNull(message = "Package seat count cannot be null")
    private Integer availableCount;
}
