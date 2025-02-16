package com.strechdstudio.app.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;

    @ManyToOne
    @JoinColumn(name = "statusId", referencedColumnName = "codeLkupId")
    private CodeLkup status;

    @ManyToOne
    @JoinColumn(name = "membershipTypeId", referencedColumnName = "codeLkupId")
    private CodeLkup membershipType;

    private LocalDateTime registrationDate;
    private LocalDateTime lastLoginDate;
    private Integer totalClassesAttended;

    @ManyToOne
    @JoinColumn(name = "currentPackageId", referencedColumnName = "customerPackageId")
    private CustomerPackage currentPackage;

    private String preferredContactMethod;
    private String city;
    private String state;
    private String country;

    private String addWho;
    private LocalDateTime addDate;
    private String editWho;
    private LocalDateTime editDate;

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
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

    public CodeLkup getStatus() {
        return status;
    }

    public void setStatus(CodeLkup status) {
        this.status = status;
    }

    public CodeLkup getMembershipType() {
        return membershipType;
    }

    public void setMembershipType(CodeLkup membershipType) {
        this.membershipType = membershipType;
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

    public CustomerPackage getCurrentPackage() {
        return currentPackage;
    }

    public void setCurrentPackage(CustomerPackage currentPackage) {
        this.currentPackage = currentPackage;
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
}
