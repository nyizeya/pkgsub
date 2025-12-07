package com.pkgsub.subscriptionsystem.common.dto.response;

import com.pkgsub.subscriptionsystem.common.enumerations.AppUserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private String id;
    private String email;
    private String username;
    private Boolean active;
    private AppUserRole role;
    private BigDecimal balance;
}