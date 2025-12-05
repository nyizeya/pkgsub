package com.pkgsub.subscriptionsystem.subscriptionservice.repository;

import com.pkgsub.subscriptionsystem.subscriptionservice.entity.WishlistEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WishlistRepository extends JpaRepository<WishlistEntry, String> {
   List<WishlistEntry> findByPackageIdOrderByCreatedAtAsc(String packageId);
}