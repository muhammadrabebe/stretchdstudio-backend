package com.strechdstudio.app.dto;

public class InstructorDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String bio;
    private String addWho;
    private String editWho;
    private Integer statusid; // Foreign key reference to CodeLkup
    private Integer specializationid; // Foreign key reference to CodeLkup

    // Default constructor
    public InstructorDTO() {
    }

    // Getters and Setters
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

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getAddWho() {
        return addWho;
    }

    public void setAddWho(String addWho) {
        this.addWho = addWho;
    }

    public String getEditWho() {
        return editWho;
    }

    public void setEditWho(String editWho) {
        this.editWho = editWho;
    }

    public Integer getStatusid() {
        return statusid;
    }

    public void setStatusid(Integer statusid) {
        this.statusid = statusid;
    }

    public Integer getSpecializationid() {
        return specializationid;
    }

    public void setSpecializationid(Integer specializationid) {
        this.specializationid = specializationid;
    }
}

