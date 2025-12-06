package com.marti.application.dtos.auth;

public class SignUpRequest {

    private final String username;
    private final String email;
    private final String passwordHash;

    public SignUpRequest(String username, String email, String passwordHash) {
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }
}
