package com.marti.application.dtos.auth;

public class LogInRequest {
    private  String email;
    private  String passwordHash;

    public LogInRequest(String email, String passwordHash) {
        this.email = email;
        this.passwordHash = passwordHash;
    }

    public String getEmail() {
        return email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }
}
