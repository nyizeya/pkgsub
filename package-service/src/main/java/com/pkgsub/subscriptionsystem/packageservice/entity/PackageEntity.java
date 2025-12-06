package com.pkgsub.subscriptionsystem.packageservice.entity;

import com.pkgsub.subscriptionsystem.common.entity.audit.Auditable;
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
@Table(name = "app_packages")
@EntityListeners(AuditingEntityListener.class)
public class PackageEntity extends Auditable implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable=false)
    private String name;

    @Column(nullable=false)
    private LocalDate openedDate;

    @Column(nullable=false)
    private LocalDate closedDate;

    @Column(nullable=false)
    private Integer permittedCount;

    @Column(nullable=false)
    private Integer availableCount;

    @Column(nullable=false)
    private BigDecimal price;
}
