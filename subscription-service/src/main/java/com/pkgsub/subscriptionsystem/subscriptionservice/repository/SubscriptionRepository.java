package com.pkgsub.subscriptionsystem.subscriptionservice.repository;

import com.pkgsub.subscriptionsystem.subscriptionservice.entity.SubscriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SubscriptionRepository extends JpaRepository<SubscriptionEntity, String> {
    List<SubscriptionEntity> findByUserId(String userId);
    Optional<SubscriptionEntity> findByPackageId(String packageId);
}
