package com.pkgsub.subscriptionsystem.subscriptionservice.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@Table(name = "package_wishlist")
public class WishlistEntry {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;
  private String packageId;
  private String userId;
  private LocalDate createdAt;
  private Boolean notified = false; // whether user was notified for an available slot
}
