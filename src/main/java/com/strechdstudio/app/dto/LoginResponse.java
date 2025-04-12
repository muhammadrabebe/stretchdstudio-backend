package com.strechdstudio.app.dto;

import com.strechdstudio.app.model.User;

import java.time.LocalDateTime;
import java.util.UUID;

public class LoginResponse {
    private String token;
    private UUID userId;
    private Integer customerId;
    private String customerName;
    private String username;
    private String email;
    private LocalDateTime lastLogin;

    public LoginResponse(String token, User user) {
        this.token = token;
        this.userId = user.getId();
        this.customerId = user.getCustomer().getCustomerId();
        this.customerName = user.getCustomer().getFullname();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.lastLogin = user.getLastLogin();
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }
}
