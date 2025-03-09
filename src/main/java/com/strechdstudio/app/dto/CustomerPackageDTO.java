package com.strechdstudio.app.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class CustomerPackageDTO {

    private UUID id;
    private Integer customerId;
    private UUID classPackageId;
    private int remainingClasses;
    private LocalDateTime purchaseDate;

    public CustomerPackageDTO() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public UUID getClassPackageId() {
        return classPackageId;
    }

    public void setClassPackageId(UUID classPackageId) {
        this.classPackageId = classPackageId;
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
