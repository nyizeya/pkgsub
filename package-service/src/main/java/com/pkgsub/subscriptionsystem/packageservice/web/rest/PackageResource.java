package com.pkgsub.subscriptionsystem.packageservice.web.rest;

import com.pkgsub.subscriptionsystem.common.dto.request.PackageCreateRequest;
import com.pkgsub.subscriptionsystem.common.dto.request.PackageSubscriberCountUpdateRequest;
import com.pkgsub.subscriptionsystem.common.dto.response.ApiResponse;
import com.pkgsub.subscriptionsystem.common.dto.response.PackageDto;
import com.pkgsub.subscriptionsystem.packageservice.service.PackageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/packages")
public class PackageResource {

    private final PackageService packageService;

    @PostMapping
    public ResponseEntity<ApiResponse<PackageDto>> createPackage(@Valid @RequestBody PackageCreateRequest dto) {
        PackageDto createdPackage = packageService.createPackage(dto);
        return new ResponseEntity<>(ApiResponse.of(HttpStatus.CREATED, null, createdPackage), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PackageDto>> getPackage(@PathVariable String id) {
        PackageDto pkg = packageService.getPackage(id);
        return ResponseEntity.ok(ApiResponse.success(pkg));
    }

    @PutMapping("/subscriberCount")
    public ResponseEntity<ApiResponse<PackageDto>> updatePackageSubscriberCount(@Valid @RequestBody PackageSubscriberCountUpdateRequest packageSubscriberCountUpdateRequest) {
        return ResponseEntity.ok(ApiResponse.success(packageService.updatePackageSubscribers(packageSubscriberCountUpdateRequest)));
    }

}
