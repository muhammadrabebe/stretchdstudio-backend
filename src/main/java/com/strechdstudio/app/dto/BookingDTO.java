package com.strechdstudio.app.dto;

import com.strechdstudio.app.model.Booking;

import java.time.LocalDateTime;

public class BookingDTO {
    private Integer bookingId;
    private Integer customerId;
    private Integer classId;
    private Integer statusId;
    private Integer instructorId; // New field
    private String className;
    private String instructorName;
    private String customerName;
    private String status;
    private LocalDateTime startTime;
    private String addWho; // New field
    private LocalDateTime addDate; // New field
    private String editWho; // New field
    private LocalDateTime editDate; // New field


    public BookingDTO() {}

    public BookingDTO(Booking booking) {
        this.bookingId = booking.getBookingId();
        this.customerId = booking.getCustomer().getCustomerId();
        this.classId = booking.getBookedClass().getClassId();
        this.statusId = booking.getStatus().getCodeLkupId();
        this.instructorId = booking.getInstructor().getInstructorId(); // Set instructorId
        this.instructorName = booking.getInstructor().getFullname();
        this.customerName = booking.getCustomer().getFullname();
        this.className = booking.getBookedClass().getClassName();
        this.status = booking.getStatus().getCode();
        this.startTime = booking.getBookedClass().getStartTime();
        this.addWho = booking.getAddWho();
        this.addDate = booking.getAddDate();
        this.editWho = booking.getEditWho();
        this.editDate = booking.getEditDate();
    }


//    public BookingDTO(Integer bookingId, Integer customerId, Integer classId, Integer statusId, Integer instructorId, String className, String instructorName, String customerName, String status, LocalDateTime startTime, String addWho, LocalDateTime addDate, String editWho, LocalDateTime editDate) {
//        this.bookingId = bookingId;
//        this.customerId = customerId;
//        this.classId = classId;
//        this.statusId = statusId;
//        this.instructorId = instructorId;
//        this.className = className;
//        this.instructorName = instructorName;
//        this.customerName = customerName;
//        this.status = status;
//        this.startTime = startTime;
//        this.addWho = addWho;
//        this.addDate = addDate;
//        this.editWho = editWho;
//        this.editDate = editDate;
//    }

    public Integer getBookingId() {
        return bookingId;
    }

    public void setBookingId(Integer bookingId) {
        this.bookingId = bookingId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public Integer getInstructorId() {
        return instructorId;
    }

    public void setInstructorId(Integer instructorId) {
        this.instructorId = instructorId;
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

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

}
