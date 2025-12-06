package com.pkgsub.subscriptionsystem.subscriptionservice.entity;

import com.pkgsub.subscriptionsystem.common.entity.audit.Auditable;
import com.pkgsub.subscriptionsystem.common.enumerations.SubscriptionStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "app_subscriptions",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "package_id"})
)
@EntityListeners(AuditingEntityListener.class)
public class SubscriptionEntity extends Auditable implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @Column(name = "user_id", nullable = false)
  private String userId;

  @Column(name = "package_id", nullable = false)
  private String packageId;

  @Column(nullable = false)
  private BigDecimal amount;

  @Enumerated(EnumType.STRING)
  private SubscriptionStatus status;

  private LocalDate refundedAt;
}
