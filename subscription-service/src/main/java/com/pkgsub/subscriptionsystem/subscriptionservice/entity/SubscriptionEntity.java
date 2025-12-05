package com.pkgsub.subscriptionsystem.subscriptionservice.entity;

import com.pkgsub.subscriptionsystem.common.enumerations.SubscriptionStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "subscriptions",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "package_id"})
)
public class SubscriptionEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @Column(name = "user_id", nullable = false)
  private String userId;

  @Column(name = "package_id", nullable = false)
  private String packageId;

  private BigDecimal amount;

  private LocalDate createdAt;

  @Enumerated(EnumType.STRING)
  private SubscriptionStatus status;

  private LocalDate refundedAt;

  private String externalTransactionId; // from billing
}
