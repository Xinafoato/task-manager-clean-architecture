package com.marti.application.usecases.auth;

import com.marti.domain.repository.UserRepository;


public class DeleteAccountUseCase {
    private final UserRepository userRepo;

    public DeleteAccountUseCase(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public void execute(String userId) {
        if (userId == null || userId.isBlank()) {
            throw new IllegalArgumentException("User ID cannot be null or empty");
        }

        userRepo.findById(userId);

    }
}
