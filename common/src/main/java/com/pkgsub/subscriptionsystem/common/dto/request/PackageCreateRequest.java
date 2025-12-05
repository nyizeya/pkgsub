package com.pkgsub.subscriptionsystem.common.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PackageCreateRequest {

    private String id;

    @NotNull(message = "Package name cannot be null")
    private String name;

    @NotNull(message = "Package opened date cannot be null")
    private LocalDate openedDate;

    @NotNull(message = "Package closed date cannot be null")
    private LocalDate closedDate;

    @NotNull(message = "Package seat count cannot be null")
    private Integer permittedCount;

    @NotNull(message = "Package price cannot be null")
    private BigDecimal price;
}