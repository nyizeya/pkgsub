package com.pkgsub.subscriptionsystem.common.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserBalanceCreditRequest {
    @NotEmpty(message = "User id cannot be empty")
    private String userId;

    @NotNull(message = "Credit amount cannot be null")
    private BigDecimal amount;
}
