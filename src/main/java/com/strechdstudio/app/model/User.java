package com.strechdstudio.app.model;

import jakarta.persistence.Entity;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;  // Unique username or email for login
    private String password;  // Hashed password

    @Enumerated(EnumType.STRING)
    private Role role;  // User's role (ADMIN, CUSTOMER, INSTRUCTOR)

    private LocalDateTime lastLoginDate;
    private LocalDateTime logoutDate;

    @OneToOne
    @JoinColumn(name = "customerId", nullable = true)  // Link to customer table if it's a customer user
    private Customer customer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public LocalDateTime getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(LocalDateTime lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public LocalDateTime getLogoutDate() {
        return logoutDate;
    }

    public void setLogoutDate(LocalDateTime logoutDate) {
        this.logoutDate = logoutDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}

