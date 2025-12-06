package com.pkgsub.subscriptionsystem.common.dto.response;

import lombok.Data;

import java.util.Set;

@Data
public class LoginResponseDTO {
    private String username;
    private String jwtToken;
    private Set<String> roles;

    public LoginResponseDTO(String username, String jwtToken, Set<String> roles) {
        this.username = username;
        this.jwtToken = jwtToken;
        this.roles = roles;
    }
}