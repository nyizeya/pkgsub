package com.pkgsub.subscriptionsystem.common.dto.request;

import lombok.Data;

@Data
public class UserCreateRequest {
    private String username;
    private String password;
    private String email;
}