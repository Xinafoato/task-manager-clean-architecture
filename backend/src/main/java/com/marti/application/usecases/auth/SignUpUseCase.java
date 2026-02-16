package com.marti.application.usecases.auth;

import com.marti.domain.model.User;
import com.marti.domain.repository.UserRepository;
import com.marti.application.dtos.auth.*;

public class SignUpUseCase {

    private final UserRepository userRepo;

    public SignUpUseCase(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public SignUpResponse execute(SignUpRequest request) {

        if (request.getUsername() == null || request.getUsername().isBlank()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }

        if (request.getEmail() == null || request.getEmail().isBlank()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }

        if (request.getPasswordHash() == null || request.getPasswordHash().isBlank()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }

        if (userRepo.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("An account with this email already exists");
        }

        User user = User.create(request.getUsername(), request.getEmail(), request.getPasswordHash());

        userRepo.save(user);

        return new SignUpResponse(user.getId(), user.getUsername(), user.getEmail());
    }
}
