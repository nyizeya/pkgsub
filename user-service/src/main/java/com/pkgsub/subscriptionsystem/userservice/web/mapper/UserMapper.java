package com.pkgsub.subscriptionsystem.userservice.web.mapper;

import com.pkgsub.subscriptionsystem.common.dto.mapper.BaseMapper;
import com.pkgsub.subscriptionsystem.common.dto.response.UserDto;
import com.pkgsub.subscriptionsystem.userservice.entity.AppUserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper extends BaseMapper<UserDto, AppUserEntity> {
}
