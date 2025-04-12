package com.strechdstudio.app.dto;

import com.strechdstudio.app.model.Booking;
import com.strechdstudio.app.util.DateTimeUtils;
import com.strechdstudio.app.util.StringFormatter;

import java.time.Duration;
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
    private String customerPhoneNumber;
    private String status;
    private String classDuration;
    private LocalDateTime startTime;
    private String classDate;
    private String bookingDate;
    private LocalDateTime endTime;
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
        this.customerPhoneNumber = StringFormatter.formatPhoneNumber(booking.getCustomer().getPhoneNumber());
        this.className = booking.getBookedClass().getClassName();
        this.status = booking.getStatus().getCode();
        this.startTime = booking.getBookedClass().getStartTime();
        this.classDate = DateTimeUtils.formatDateTime(booking.getBookedClass().getStartTime());
        this.bookingDate = DateTimeUtils.formatDateTime(booking.getAddDate());
        this.endTime = booking.getBookedClass().getEndTime(); // Get endTime

        // Calculate class duration
        if (startTime != null && endTime != null) {
            Duration duration = Duration.between(startTime, endTime);
            long hours = duration.toHours();
            long minutes = duration.toMinutesPart();
            if (hours == 1){
                this.classDuration = String.format("%d hour", hours);
            }else if (hours > 0) {
                this.classDuration = String.format("%d hour%s %d mins", hours, hours > 1 ? "s" : "", minutes);
            } else {
                this.classDuration = String.format("%d mins", minutes);
            }
        } else {
            this.classDuration = "Unknown";
        }

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


    public String getCustomerPhoneNumber() {
        return customerPhoneNumber;
    }

    public void setCustomerPhoneNumber(String customerPhoneNumber) {
        this.customerPhoneNumber = customerPhoneNumber;
    }

    public String getClassDate() {
        return classDate;
    }

    public void setClassDate(String classDate) {
        this.classDate = classDate;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public String getClassDuration() {
        return classDuration;
    }

    public void setClassDuration(String classDuration) {
        this.classDuration = classDuration;
    }

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
