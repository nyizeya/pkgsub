package com.pkgsub.subscriptionsystem.billingservice.web.mapper;

import com.pkgsub.subscriptionsystem.common.dto.mapper.BaseMapper;
import com.pkgsub.subscriptionsystem.common.dto.response.UserDto;
import com.pkgsub.subscriptionsystem.billingservice.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper extends BaseMapper<UserDto, User> {
}
