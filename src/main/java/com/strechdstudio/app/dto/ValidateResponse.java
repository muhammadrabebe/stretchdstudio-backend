package com.strechdstudio.app.dto;

public class ValidateResponse {

    String email;
    Boolean isUserExists;

    public ValidateResponse() {
    }

    public ValidateResponse(String email, Boolean isUserExists) {
        this.email = email;
        this.isUserExists = isUserExists;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getUserExists() {
        return isUserExists;
    }

    public void setUserExists(Boolean userExists) {
        isUserExists = userExists;
    }
}
