package com.pkgsub.subscriptionsystem.subscriptionservice.client;

import com.pkgsub.subscriptionsystem.common.dto.request.PackageSubscriberCountUpdateRequest;
import com.pkgsub.subscriptionsystem.common.dto.response.ApiResponse;
import com.pkgsub.subscriptionsystem.common.dto.response.PackageDto;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "package-service", path = "/api/packages")
public interface PackageClient {
    @GetMapping("/{id}")
    ApiResponse<PackageDto> getPackage(@PathVariable String id);

    @PutMapping("/subscriberCount")
    ApiResponse<PackageDto> updatePackageSubscriberCount(@Valid @RequestBody PackageSubscriberCountUpdateRequest dto);
}
