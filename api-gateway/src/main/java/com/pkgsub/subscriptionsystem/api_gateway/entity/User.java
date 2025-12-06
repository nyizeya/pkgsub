package com.pkgsub.subscriptionsystem.api_gateway.entity;

import com.pkgsub.subscriptionsystem.common.entity.audit.Auditable;
import com.pkgsub.subscriptionsystem.common.enumerations.AppUserRole;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Setter
@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(
        name = "app_users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "email"),
                @UniqueConstraint(columnNames = "username")
        }
)
public class User extends Auditable implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false, columnDefinition = "VARCHAR(30)")
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, columnDefinition = "VARCHAR(30)")
    private String email;

    @Column(nullable = false)
    private BigDecimal balance = new BigDecimal(10000);

    @Enumerated(EnumType.STRING)
    @Column(name = "app_user_role", columnDefinition = "VARCHAR(5)")
    private AppUserRole role = AppUserRole.USER;

    @Column(name = "enabled")
    private boolean enabled = true;

    @Column(name = "account_non_locked")
    private boolean accountNonLocked = true;

    @Column(name = "account_non_expired")
    private boolean accountNonExpired = true;

    @Column(name = "credentials_non_expired")
    private boolean credentialsNonExpired = true;

    @Column(name = "credentials_expiry_date")
    private LocalDate credentialsExpiryDate = LocalDate.now().plusYears(1);

    @Column(name = "account_expiry_date")
    private LocalDate accountExpiryDate = LocalDate.now().plusYears(1);

}