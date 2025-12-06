package com.pkgsub.subscriptionsystem.common.dto.request;

import jakarta.validation.constraints.NotEmpty;
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

    @NotEmpty(message = "Package name cannot be empty")
    private String name;

    @NotNull(message = "Package opened date cannot be empty")
    private LocalDate openedDate;

    @NotNull(message = "Package closed date cannot be empty")
    private LocalDate closedDate;

    @NotNull(message = "Package seat count cannot be empty")
    private Integer permittedCount;

    @NotNull(message = "Package price cannot be empty")
    private BigDecimal price;
}