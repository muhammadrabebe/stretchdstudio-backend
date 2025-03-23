package com.strechdstudio.app.dto;

public class LoginRequest {

    private String username;
    private String email;
    private String password;

    // Default constructor
    public LoginRequest() {}

    // Constructor
    public LoginRequest(String username, String password , String email) {
        this.username = username;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Getters and setters
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
}

