package com.strechdstudio.app.dto;

import com.strechdstudio.app.model.Customer;

import java.time.LocalDateTime;

public class CustomerDTO {
    private Integer customerId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private Integer statusId;  // Reference to CodeLkup statusId
    private String status;  // Reference to CodeLkup statusId
    private Integer membershipTypeId;  // Reference to CodeLkup membershipTypeId
    private String membershipType;  // Reference to CodeLkup membershipTypeId
    private LocalDateTime registrationDate;
    private LocalDateTime lastLoginDate;
    private Integer totalClassesAttended;
    private String preferredContactMethod;
    private String city;
    private String state;
    private String country;
    private String addWho;
    private LocalDateTime addDate;
    private String editWho;
    private LocalDateTime editDate;

    public CustomerDTO() {
    }

    public CustomerDTO(Customer customer) {
        this.customerId = customer.getCustomerId();
        this.firstName = customer.getFirstName();
        this.lastName = customer.getLastName();
        this.email = customer.getEmail();
        this.phoneNumber = customer.getPhoneNumber();
        this.statusId = customer.getStatus() != null ? customer.getStatus().getCodeLkupId() : null;
        this.status = customer.getStatus().getCode();
        this.membershipTypeId = customer.getMembershipType() != null ? customer.getMembershipType().getCodeLkupId() : null;
        this.membershipType = customer.getMembershipType().getCode();
        this.registrationDate = customer.getRegistrationDate();
        this.lastLoginDate = customer.getLastLoginDate();
        this.totalClassesAttended = customer.getTotalClassesAttended();
        this.preferredContactMethod = customer.getPreferredContactMethod();
        this.city = customer.getCity();
        this.state = customer.getState();
        this.country = customer.getCountry();
        this.addWho = customer.getAddWho();
        this.addDate = customer.getAddDate();
        this.editWho = customer.getEditWho();
        this.editDate = customer.getEditDate();
    }

    // Getters and Setters
    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public Integer getMembershipTypeId() {
        return membershipTypeId;
    }

    public void setMembershipTypeId(Integer membershipTypeId) {
        this.membershipTypeId = membershipTypeId;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    public LocalDateTime getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(LocalDateTime lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public Integer getTotalClassesAttended() {
        return totalClassesAttended;
    }

    public void setTotalClassesAttended(Integer totalClassesAttended) {
        this.totalClassesAttended = totalClassesAttended;
    }

    public String getPreferredContactMethod() {
        return preferredContactMethod;
    }

    public void setPreferredContactMethod(String preferredContactMethod) {
        this.preferredContactMethod = preferredContactMethod;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMembershipType() {
        return membershipType;
    }

    public void setMembershipType(String membershipType) {
        this.membershipType = membershipType;
    }
}
