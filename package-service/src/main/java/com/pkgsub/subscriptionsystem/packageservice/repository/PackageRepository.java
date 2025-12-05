package com.pkgsub.subscriptionsystem.packageservice.repository;

import com.pkgsub.subscriptionsystem.packageservice.entity.PackageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PackageRepository extends JpaRepository<PackageEntity, String> {

}
