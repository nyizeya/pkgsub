package com.pkgsub.subscriptionsystem.common.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignUpRequest {
    @NotEmpty(message = "Username cannot be empty")
    private String username;

    @Size(min = 8, message = "Password should be at least 8 characters long")
    @NotEmpty(message = "Password cannot be empty")
    private String password;

    @Email(message = "Please type in a valid email")
    @NotEmpty(message = "Email cannot be null")
    private String email;
}