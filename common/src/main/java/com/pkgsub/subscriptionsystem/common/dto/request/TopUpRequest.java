package com.pkgsub.subscriptionsystem.common.dto.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TopUpRequest {
    private String userId;
    private BigDecimal amount;
}
