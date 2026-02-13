package com.marti.application.usecases.auth;

import com.marti.application.dtos.auth.*;
import com.marti.domain.model.User;
import com.marti.domain.repository.UserRepository;


public class LogInUseCase {
    private final UserRepository userRepo;

    public LogInUseCase(UserRepository userRepo) {

        this.userRepo = userRepo;
    }

    public LogInResponse execute(LogInRequest request) {

        if (request.getEmail() == null || request.getEmail().isBlank()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }
        if (request.getPasswordHash() == null || request.getPasswordHash().isBlank()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }

        User user = userRepo.findByEmail(request.getEmail()).orElseThrow(() -> new IllegalArgumentException("User or password incorrect"));
        if (user == null) {
            throw new IllegalArgumentException("Invalid email");
        }
        if (!user.getPasswordHash().equals(request.getPasswordHash())) {
            throw new IllegalArgumentException("Invalid Password");
        }
        return new LogInResponse(user.getId(), user.getUsername(), user.getEmail());
    }
}
