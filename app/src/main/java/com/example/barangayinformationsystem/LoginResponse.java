package com.example.barangayinformationsystem;

public class LoginResponse {
    private String status;
    private int id;
    private String accountStatus;
    private String message;

    public String getStatus() {
        return status;
    }

    public int getId() {
        return id;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public String getMessage() {
        return message;
    }
}