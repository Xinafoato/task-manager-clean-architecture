package com.marti.application.dtos.auth;

public class LogInRequest {
    private final String email;
    private final String passwordHash;

    public LogInRequest(String username, String email, String passwordHash) {
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
