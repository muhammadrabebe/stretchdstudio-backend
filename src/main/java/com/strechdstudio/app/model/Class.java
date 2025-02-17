package com.strechdstudio.app.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "classes")

public class Class {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "classid")
    private Integer classId;

    @Column(name = "classname", nullable = false)
    private String className;

    @Column(name = "starttime", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "endtime", nullable = false)
    private LocalDateTime endTime;

    @Column(name = "maxcapacity")
    private Integer maxCapacity;

    @Column(name = "location")
    private String location;

    @Column(name = "addwho")
    private String addWho;

    @Column(name = "adddate")
    private LocalDateTime addDate;

    @Column(name = "editwho")
    private String editWho;

    @Column(name = "editdate")
    private LocalDateTime editDate;

    @ManyToOne
    @JoinColumn(name = "instructorid", referencedColumnName = "instructorid")
    private Instructor instructor; // Assuming an Instructor entity exists

    @ManyToOne
    @JoinColumn(name = "branchid", referencedColumnName = "codelkupid")
    private CodeLkup branchid; // Assuming a CodeLookup entity exists

    @Column(name = "addressone")
    private String addressOne;

    @Column(name = "addresstwo")
    private String addressTwo;

    @Column(name = "addressthree")
    private String addressThree;

    @Column(name = "city")
    private String city;

    @ManyToOne
    @JoinColumn(name = "statusid", referencedColumnName = "codelkupid")
    private CodeLkup statusid; // Assuming status is stored in codelkup

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

    public Instructor getInstructor() {
        return instructor;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    public CodeLkup getBranchid() {
        return branchid;
    }

    public void setBranchid(CodeLkup branchid) {
        this.branchid = branchid;
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

    public CodeLkup getStatusid() {
        return statusid;
    }

    public void setStatusid(CodeLkup statusid) {
        this.statusid = statusid;
    }
}
