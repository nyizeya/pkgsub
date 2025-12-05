package com.pkgsub.subscriptionsystem.userservice.repository;

import com.pkgsub.subscriptionsystem.userservice.entity.AppUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<AppUserEntity, String> {

    Optional<AppUserEntity> findByUsername(String username);

    Optional<AppUserEntity> findByEmail(String email);
}
