package com.pkgsub.subscriptionsystem.subscriptionservice.repository;

import com.pkgsub.subscriptionsystem.common.enumerations.SubscriptionStatus;
import com.pkgsub.subscriptionsystem.subscriptionservice.entity.SubscriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SubscriptionRepository extends JpaRepository<SubscriptionEntity, String> {
    Optional<SubscriptionEntity> findByUserIdAndPackageId(Long userId, Long packageId);
    List<SubscriptionEntity> findByPackageIdAndStatus(Long packageId, SubscriptionStatus status);
}
