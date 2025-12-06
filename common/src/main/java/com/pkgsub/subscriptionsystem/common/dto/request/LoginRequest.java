package com.pkgsub.subscriptionsystem.common.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginRequest {
    @Size(min = 3, max = 20)
    @NotBlank(message = "Username must not be blank")
    private String username;

    @Size(min = 8, message = "Password should be at least 8 characters long")
    @NotBlank(message = "Password must not be blank")
    private String password;
}