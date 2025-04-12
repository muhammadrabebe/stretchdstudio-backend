package com.strechdstudio.app.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class CustomerPackageDTO {

    private UUID customerPackageId;
    private Integer customerId;
    private String customerName;
    private String classPackageName;
    private UUID classPackageId;
    private String status;
    private int remainingClasses;
    private int totalClasses;
    private BigDecimal price;
    private LocalDateTime purchaseDate;
    private LocalDateTime startDate;
    private LocalDateTime expiryDate;
    private Boolean isExpired;

    public CustomerPackageDTO() {
    }

    public Boolean getIsExpired() {
        return isExpired;
    }

    public void setIsExpired(Boolean expired) {
        isExpired = expired;
    }

    public int getTotalClasses() {
        return totalClasses;
    }

    public void setTotalClasses(int totalClasses) {
        this.totalClasses = totalClasses;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }

    public UUID getCustomerPackageId() {
        return customerPackageId;
    }

    public void setCustomerPackageId(UUID customerPackageId) {
        this.customerPackageId = customerPackageId;
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

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getClassPackageName() {
        return classPackageName;
    }

    public void setClassPackageName(String classPackageName) {
        this.classPackageName = classPackageName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
