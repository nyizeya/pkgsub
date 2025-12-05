package com.pkgsub.subscriptionsystem.userservice.entity;

import com.pkgsub.subscriptionsystem.common.entity.audit.Auditable;
import com.pkgsub.subscriptionsystem.common.enumerations.AppUserRole;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "app_users")
@EntityListeners(AuditingEntityListener.class)
public class AppUserEntity extends Auditable implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String encodedPassword;

    @Column(nullable = false, unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AppUserRole role;

    @Column(nullable = false)
    private Boolean active;

    @Column(nullable = false)
    private BigDecimal balance;
}
