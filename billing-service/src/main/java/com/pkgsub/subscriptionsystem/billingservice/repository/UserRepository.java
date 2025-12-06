package com.pkgsub.subscriptionsystem.billingservice.repository;

import com.pkgsub.subscriptionsystem.billingservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
