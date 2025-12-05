package com.pkgsub.subscriptionsystem.common.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PackageDto {
    private String id;
    private String name;
    private LocalDate openedDate, closedDate;
    private Integer permittedCount, availableCount;
    private BigDecimal price;
}
