package com.pkgsub.subscriptionsystem.packageservice.entity;

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
@Table(name = "packages")
public class PackageEntity {
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
