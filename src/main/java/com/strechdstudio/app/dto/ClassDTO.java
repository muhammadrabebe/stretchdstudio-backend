package com.strechdstudio.app.dto;

import com.strechdstudio.app.model.Class;
import com.strechdstudio.app.util.DateTimeUtils;

import java.time.LocalDateTime;
import java.util.UUID;

public class ClassDTO {

    private Integer classId;
    private String className;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Integer maxCapacity;
    private String location;
    private String addWho;
    private LocalDateTime addDate;
    private String editWho;
    private LocalDateTime editDate;
    private String addressOne;
    private String addressTwo;
    private String addressThree;
    private String city;
    private Integer instructorId;
    private Integer branchId;
    private Integer statusId;
    private String description;
    private Integer minAge;
    private Integer maxAge;
    private String instructorName; // ✅ Instructor name instead of ID
    private String branchName; // ✅ Branch name instead of ID
    private String statusName; // ✅ Status name instead of ID
    private String formattedStartTime;
    private String formattedEndTime;
    private UUID typeId;
    private String classTypeName;

    public ClassDTO() {
    }

    public ClassDTO(Class c) {
        this.classId = c.getClassId();
        this.className = c.getClassName();
        this.startTime = c.getStartTime();
        this.endTime = c.getEndTime();
        this.formattedStartTime = DateTimeUtils.formatDateTime(c.getStartTime());
        this.formattedEndTime = DateTimeUtils.formatDateTime(c.getEndTime());
        this.maxCapacity = c.getMaxCapacity();
        this.location = c.getLocation();
        this.instructorId = c.getInstructor() != null ? c.getInstructor().getInstructorId() : null;
        this.branchId = c.getBranch() != null ? c.getBranch().getCodeLkupId() : null;
        this.statusId = c.getStatus() != null ? c.getStatus().getCodeLkupId() : null;
        this.typeId = c.getClassType() != null ? c.getClassType().getTypeId() : null;
        this.instructorName = c.getInstructor() != null ? c.getInstructor().getFullname() : null;
        this.statusName = c.getStatus() != null ? c.getStatus().getCode() : null;
        this.branchName = c.getBranch() != null ? c.getBranch().getCode() : null;
        this.classTypeName = c.getClassType() != null ? c.getClassType().getType() : null;
        this.addressOne = c.getAddressOne();
        this.addressTwo = c.getAddressTwo();
        this.addressThree = c.getAddressThree();
        this.city = c.getCity();
        this.description = c.getDescription();
        this.minAge = c.getMinAge();
        this.maxAge = c.getMaxAge();
        this.addWho = c.getAddWho();
        this.addDate = c.getAddDate();
        this.editWho = c.getEditWho();
        this.editDate = c.getEditDate();
    }

    // Getters and Setters
    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Integer getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(Integer maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAddWho() {
        return addWho;
    }

    public void setAddWho(String addWho) {
        this.addWho = addWho;
    }

    public LocalDateTime getAddDate() {
        return addDate;
    }

    public void setAddDate(LocalDateTime addDate) {
        this.addDate = addDate;
    }

    public String getEditWho() {
        return editWho;
    }

    public void setEditWho(String editWho) {
        this.editWho = editWho;
    }

    public LocalDateTime getEditDate() {
        return editDate;
    }

    public void setEditDate(LocalDateTime editDate) {
        this.editDate = editDate;
    }

    public String getAddressOne() {
        return addressOne;
    }

    public void setAddressOne(String addressOne) {
        this.addressOne = addressOne;
    }

    public String getAddressTwo() {
        return addressTwo;
    }

    public void setAddressTwo(String addressTwo) {
        this.addressTwo = addressTwo;
    }

    public String getAddressThree() {
        return addressThree;
    }

    public void setAddressThree(String addressThree) {
        this.addressThree = addressThree;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getInstructorId() {
        return instructorId;
    }

    public void setInstructorId(Integer instructorId) {
        this.instructorId = instructorId;
    }

    public Integer getBranchId() {
        return branchId;
    }

    public void setBranchId(Integer branchId) {
        this.branchId = branchId;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getMinAge() {
        return minAge;
    }

    public void setMinAge(Integer minAge) {
        this.minAge = minAge;
    }

    public Integer getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(Integer maxAge) {
        this.maxAge = maxAge;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getFormattedStartTime() {
        return formattedStartTime;
    }

    public void setFormattedStartTime(String formattedStartTime) {
        this.formattedStartTime = formattedStartTime;
    }

    public String getFormattedEndTime() {
        return formattedEndTime;
    }

    public void setFormattedEndTime(String formattedEndTime) {
        this.formattedEndTime = formattedEndTime;
    }

    public UUID getTypeId() {
        return typeId;
    }

    public void setTypeId(UUID typeId) {
        this.typeId = typeId;
    }

    public String getClassTypeName() {
        return classTypeName;
    }

    public void setClassTypeName(String classTypeName) {
        this.classTypeName = classTypeName;
    }
}
