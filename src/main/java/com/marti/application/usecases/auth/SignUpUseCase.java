package com.marti.application.usecases.auth;

import com.marti.domain.model.User;
import com.marti.domain.service.DomainController;
import com.marti.application.dtos.auth.*;

public class SignUpUseCase {

    private final DomainController domainController;

    public SignUpUseCase(DomainController domainController) {
        this.domainController = domainController;
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


        User user = domainController.signUp(request.getUsername(), request.getEmail(), request.getPasswordHash());

        return new SignUpResponse(user.getId(), user.getUsername(), user.getEmail());
    }
}

