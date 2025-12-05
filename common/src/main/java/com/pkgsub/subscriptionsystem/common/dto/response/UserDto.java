package com.pkgsub.subscriptionsystem.common.dto.response;

import com.pkgsub.subscriptionsystem.common.enumerations.AppUserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private String id;
    private String username;
    private String email;
    private AppUserRole role;
    private Boolean active;
}