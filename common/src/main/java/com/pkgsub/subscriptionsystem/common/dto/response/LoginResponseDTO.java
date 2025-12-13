package com.pkgsub.subscriptionsystem.common.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;

@Data
public class LoginResponseDTO {
    private String id;
    private String email;
    private Boolean status;
    private String username;
    private String jwtToken;
    private Set<String> roles;
    private BigDecimal balance;

    public LoginResponseDTO(String id, String email, String username, String jwtToken, Set<String> roles, BigDecimal balance, Boolean status) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.jwtToken = jwtToken;
        this.roles = roles;
        this.balance = balance;
        this.status = status;
    }
}