package com.strechdstudio.app.model;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "customerPackage")
public class CustomerPackage {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false)
    private UUID customerPackageId;

    @ManyToOne
    @JoinColumn(name = "customerId", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "classPackageId", nullable = false)
    private ClassPackage classPackage;

    private int remainingClasses;
    private LocalDateTime purchaseDate = LocalDateTime.now();
}
