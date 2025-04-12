package com.strechdstudio.app.dto;

import jakarta.validation.constraints.NotBlank;

public class ValidateRequest {

    @NotBlank
    private String username;

    public ValidateRequest() {
    }

    public ValidateRequest(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
