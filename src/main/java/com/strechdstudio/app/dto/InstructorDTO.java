package com.strechdstudio.app.dto;

import com.strechdstudio.app.model.Instructor;

import java.time.LocalDateTime;

public class InstructorDTO {
    private Integer instructorId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String bio;
    private Integer statusId; // Foreign key reference to CodeLkup
    private Integer specializationId; // Foreign key reference to CodeLkup
    private String status;
    private String specialization;
    private String addWho;
    private String editWho;
    private LocalDateTime editDate;
    private LocalDateTime addDate;


    // Default constructor
    public InstructorDTO() {
    }

    public InstructorDTO(Instructor instructor) {
        this.instructorId = instructor.getInstructorId();
        this.firstName = instructor.getFirstName();
        this.lastName = instructor.getLastName();
        this.email = instructor.getEmail();
        this.phoneNumber = instructor.getPhoneNumber();
        this.bio = instructor.getBio();
        this.statusId = instructor.getStatus() !=null ? instructor.getStatus().getCodeLkupId() : null;
        this.status = instructor.getStatus() !=null ? instructor.getStatus().getCode() : null;
        this.specializationId = instructor.getSpecialization().getCodeLkupId();
        this.specialization = instructor.getSpecialization() !=null ? instructor.getSpecialization().getCode() : null;
        this.addWho = instructor.getAddWho();
        this.editWho = instructor.getEditWho();
        this.editDate = instructor.getEditDate();
        this.addDate = instructor.getAddDate();
    }

    // Getters and Setters
    public Integer getInstructorId() {
        return instructorId;
    }

    public void setInstructorId(Integer instructorId) {
        this.instructorId = instructorId;
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
        return statusId;
    }

    public void setStatusid(Integer statusid) {
        this.statusId = statusid;
    }

    public Integer getSpecializationid() {
        return specializationId;
    }

    public void setSpecializationid(Integer specializationid) {
        this.specializationId = specializationid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public LocalDateTime getEditDate() {
        return editDate;
    }

    public void setEditDate(LocalDateTime editDate) {
        this.editDate = editDate;
    }

    public LocalDateTime getAddDate() {
        return addDate;
    }

    public void setAddDate(LocalDateTime addDate) {
        this.addDate = addDate;
    }
}

