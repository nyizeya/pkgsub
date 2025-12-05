package com.pkgsub.subscriptionsystem.subscriptionservice.web.mapper;

import com.pkgsub.subscriptionsystem.common.dto.mapper.BaseMapper;
import com.pkgsub.subscriptionsystem.common.dto.response.SubscriptionDto;
import com.pkgsub.subscriptionsystem.subscriptionservice.entity.SubscriptionEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SubscriptionMapper extends BaseMapper<SubscriptionDto, SubscriptionEntity> {
}
