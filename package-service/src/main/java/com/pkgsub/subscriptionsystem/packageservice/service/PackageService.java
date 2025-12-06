package com.pkgsub.subscriptionsystem.packageservice.service;

import com.pkgsub.subscriptionsystem.common.dto.request.PackageCreateRequest;
import com.pkgsub.subscriptionsystem.common.dto.request.PackageSubscriberCountUpdateRequest;
import com.pkgsub.subscriptionsystem.common.dto.response.PackageDto;
import com.pkgsub.subscriptionsystem.packageservice.entity.PackageEntity;

import java.time.Instant;

public interface PackageService {
    PackageDto createPackage(PackageCreateRequest dto);

    PackageDto updatePackageSubscribers(PackageSubscriberCountUpdateRequest packageSubscriberCountUpdateRequest);

     // PackageDto updatePackage(Long id, PackageUpdateDto dto);

    PackageDto getPackage(String id);

    // helper
    boolean isWithinSubscriptionWindow(PackageEntity p, Instant now);
}