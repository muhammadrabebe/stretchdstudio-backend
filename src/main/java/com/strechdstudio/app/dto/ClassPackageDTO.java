package com.strechdstudio.app.dto;

import com.strechdstudio.app.model.ClassType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class ClassPackageDTO {
    private UUID classPackageId;
    private String name;
    private String description;
    private BigDecimal price;
    private UUID classTypeId;
    private String classTypeName;
    private int totalClasses;
    private LocalDateTime startDate;
    private LocalDateTime expiryDate;

    public ClassPackageDTO() {
    }

    public ClassPackageDTO(UUID classPackageId, String name, String description, BigDecimal price,
                           int totalClasses, LocalDateTime startDate, LocalDateTime expiryDate,
                           UUID classTypeId, String classTypeName) {
        this.classPackageId = classPackageId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.totalClasses = totalClasses;
        this.startDate = startDate;
        this.expiryDate = expiryDate;
        this.classTypeId = classTypeId;
        this.classTypeName = classTypeName;
    }

    public UUID getClassPackageId() {
        return classPackageId;
    }

    public void setClassPackageId(UUID classPackageId) {
        this.classPackageId = classPackageId;
    }

    public String getClassTypeName() {
        return classTypeName;
    }

    public void setClassTypeName(String classTypeName) {
        this.classTypeName = classTypeName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public UUID getClassTypeId() {
        return classTypeId;
    }

    public void setClassTypeId(UUID classTypeId) {
        this.classTypeId = classTypeId;
    }

    public int getTotalClasses() {
        return totalClasses;
    }

    public void setTotalClasses(int totalClasses) {
        this.totalClasses = totalClasses;
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

}
