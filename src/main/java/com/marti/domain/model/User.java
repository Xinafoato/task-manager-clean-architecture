package com.marti.domain.model;

import java.util.UUID;

public class User {

    private final String id;
    private String username;
    private String email;
    private String passwordHash;

    private User(String id, String username, String email, String passwordHash) {
        this.id = id != null ? id : UUID.randomUUID().toString();
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
    }


    // Factory Method
    public static User create(String username, String email, String passwordHash) {
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Invalid email");
        }
        if (passwordHash == null || passwordHash.length() < 10) {
            throw new IllegalArgumentException("Invalid hash");
        }

        return new User(null, username, email, passwordHash);
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        // Invariant mínim
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Invalid email");
        }
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        if (passwordHash == null || passwordHash.length() < 10) {
            throw new IllegalArgumentException("Invalid hash");
        }
        this.passwordHash = passwordHash;
    }
}
