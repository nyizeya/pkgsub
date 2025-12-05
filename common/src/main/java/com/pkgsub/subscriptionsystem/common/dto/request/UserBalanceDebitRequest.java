package com.pkgsub.subscriptionsystem.common.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserBalanceDebitRequest {
    @NotNull(message = "User id cannot be null")
    private String userId;

    @NotNull(message = "Debit amount cannot be null")
    private BigDecimal amount;
}
