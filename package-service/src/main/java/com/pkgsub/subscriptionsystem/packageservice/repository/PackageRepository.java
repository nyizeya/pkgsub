package com.pkgsub.subscriptionsystem.packageservice.repository;

import com.pkgsub.subscriptionsystem.packageservice.entity.PackageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PackageRepository extends JpaRepository<PackageEntity, String> {
    Optional<PackageEntity> findByName(String packageName);
}
