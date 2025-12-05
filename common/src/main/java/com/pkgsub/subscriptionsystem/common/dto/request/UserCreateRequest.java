package com.pkgsub.subscriptionsystem.common.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserCreateRequest {
    @NotNull(message = "Username cannot be null")
    private String username;

    @NotNull(message = "Password cannot be null")
    private String password;

    @Email(message = "Please type in a valid email")
    @NotNull(message = "Email cannot be null")
    private String email;
}