package com.pkgsub.subscriptionsystem.packageservice.web.mapper;

import com.pkgsub.subscriptionsystem.common.dto.mapper.BaseMapper;
import com.pkgsub.subscriptionsystem.common.dto.response.PackageDto;
import com.pkgsub.subscriptionsystem.packageservice.entity.PackageEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PackageMapper extends BaseMapper<PackageDto, PackageEntity> {
}
