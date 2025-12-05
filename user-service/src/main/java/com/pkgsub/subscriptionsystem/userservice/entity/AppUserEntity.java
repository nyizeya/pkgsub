package com.pkgsub.subscriptionsystem.userservice.entity;

import com.pkgsub.subscriptionsystem.common.enumerations.AppUserRole;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class AppUserEntity implements Serializable {

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
    private LocalDate createdAt;

    @Column(nullable = false)
    private Boolean active;

    @Column(nullable = false)
    private BigDecimal balance;
}
