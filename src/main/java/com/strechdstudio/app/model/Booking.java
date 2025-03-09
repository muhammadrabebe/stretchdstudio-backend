package com.strechdstudio.app.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bookingId;

    @ManyToOne
    @JoinColumn(name = "customerId", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "classId", nullable = false)
    private Class bookedClass; // Renaming to avoid conflict with Java keyword

    @ManyToOne
    @JoinColumn(name = "statusId", nullable = false)
    private CodeLkup status;

    @ManyToOne
    @JoinColumn(name = "instructorId", nullable = false)
    private Instructor instructor; // Linking instructor

    @Column(name = "addwho")
    private String addWho;

    @Column(name = "adddate")
    private LocalDateTime addDate;

    @Column(name = "editwho")
    private String editWho;

    @Column(name = "editdate")
    private LocalDateTime editDate;

    public Integer getBookingId() {
        return bookingId;
    }

    public void setBookingId(Integer bookingId) {
        this.bookingId = bookingId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Class getBookedClass() {
        return bookedClass;
    }

    public void setBookedClass(Class bookedClass) {
        this.bookedClass = bookedClass;
    }

    public CodeLkup getStatus() {
        return status;
    }

    public void setStatus(CodeLkup status) {
        this.status = status;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
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
