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

    public UUID getCustomerPackageId() {
        return customerPackageId;
    }

    public void setCustomerPackageId(UUID customerPackageId) {
        this.customerPackageId = customerPackageId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public ClassPackage getClassPackage() {
        return classPackage;
    }

    public void setClassPackage(ClassPackage classPackage) {
        this.classPackage = classPackage;
    }

    public int getRemainingClasses() {
        return remainingClasses;
    }

    public void setRemainingClasses(int remainingClasses) {
        this.remainingClasses = remainingClasses;
    }

    public LocalDateTime getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDateTime purchaseDate) {
        this.purchaseDate = purchaseDate;
    }
}
