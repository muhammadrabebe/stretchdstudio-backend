package com.strechdstudio.app.dto;

public class ApiResponse<T> {
    private String message;
    private int status;
    private T data;

    public ApiResponse(String message, int status, T data) {
        this.message = message;
        this.status = status;
        this.data = data;
    }

    // Getters and setters
    public String getMessage() { return message; }
    public int getStatus() { return status; }
    public T getData() { return data; }

    public void setMessage(String message) { this.message = message; }
    public void setStatus(int status) { this.status = status; }
    public void setData(T data) { this.data = data; }
}
