package com.pkgsub.subscriptionsystem.api_gateway.web.mapper;

import com.pkgsub.subscriptionsystem.api_gateway.entity.User;
import com.pkgsub.subscriptionsystem.common.dto.mapper.BaseMapper;
import com.pkgsub.subscriptionsystem.common.dto.response.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper extends BaseMapper<UserDto, User> {
}