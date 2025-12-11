package com.pkgsub.subscriptionsystem.packageservice.service.impl;

import com.pkgsub.subscriptionsystem.common.dto.request.PackageCreateRequest;
import com.pkgsub.subscriptionsystem.common.dto.request.PackageSubscriberCountUpdateRequest;
import com.pkgsub.subscriptionsystem.common.dto.response.PackageDto;
import com.pkgsub.subscriptionsystem.common.exceptions.DuplicatePackageNameException;
import com.pkgsub.subscriptionsystem.common.exceptions.EntityNotFoundException;
import com.pkgsub.subscriptionsystem.packageservice.entity.PackageEntity;
import com.pkgsub.subscriptionsystem.packageservice.repository.PackageRepository;
import com.pkgsub.subscriptionsystem.packageservice.service.PackageService;
import com.pkgsub.subscriptionsystem.packageservice.web.mapper.PackageMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PackageServiceImpl implements PackageService {

    private final PackageMapper packageMapper;
    private final PackageRepository packageRepository;

    @Override
    public List<PackageDto> getPackages() {
        return packageMapper.toDTOList(packageRepository.findAll());
    }

    @Transactional
    @Override
    public PackageDto createPackage(PackageCreateRequest dto) {
        log.info("Creating new package: {}", dto.getName());

        if (dto.getClosedDate().isBefore(dto.getOpenedDate())) {
            throw new IllegalArgumentException("Closed date cannot be before opened");
        }

        packageRepository.findByName(dto.getName()).ifPresent(packageEntity -> {
            throw new DuplicatePackageNameException(HttpStatus.CONFLICT, "Package with name [%s] already exists".formatted(dto.getName()));
        });

        var entity = PackageEntity.builder()
                .id(dto.getId())
                .name(dto.getName())
                .openedDate(dto.getOpenedDate())
                .closedDate(dto.getClosedDate())
                .price(dto.getPrice())
                .availableCount(dto.getPermittedCount())
                .permittedCount(dto.getPermittedCount())
                .build();

        entity = packageRepository.save(entity);
        return packageMapper.toDTO(entity);
    }

    @Transactional
    @Override
    public PackageDto updatePackageSubscribers(PackageSubscriberCountUpdateRequest packageSubscriberCountUpdateRequest) {
        var entity = findById(packageSubscriberCountUpdateRequest.getPkgId());
        entity.setAvailableCount(packageSubscriberCountUpdateRequest.getAvailableCount());
        return packageMapper.toDTO(packageRepository.save(entity));
    }

    @Override
    public PackageDto getPackage(String id) {
        var entity = findById(id);
        return packageMapper.toDTO(entity);
    }

    @Override
    public boolean isWithinSubscriptionWindow(PackageEntity p, Instant now) {
        LocalDate today = now.atOffset(ZoneOffset.UTC).toLocalDate();
        boolean isAfterOpen = !today.isBefore(p.getOpenedDate());
        boolean isBeforeClose = !today.isAfter(p.getClosedDate());

        log.info("Is still within subscription window ? {}", isAfterOpen && isBeforeClose);
        return isAfterOpen && isBeforeClose;
    }

    private PackageEntity findById(String id) {
        return packageRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(HttpStatus.NOT_FOUND, "Package with id [%s] is not found".formatted(id)));
    }
}
