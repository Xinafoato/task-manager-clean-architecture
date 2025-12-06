package com.marti.application.usecases.auth;

import com.marti.application.dtos.auth.*;
import com.marti.domain.model.User;
import com.marti.domain.service.DomainController;

public class LogInUseCase {
    private final DomainController domainController;

    public LogInUseCase(DomainController domainController) {
        this.domainController = domainController;
    }

    public LogInResponse execute(LogInRequest request) {

        if (request.getEmail() == null || request.getEmail().isBlank()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }
        if (request.getPasswordHash() == null || request.getPasswordHash().isBlank()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }

        User user = domainController.login(request.getEmail(), request.getPasswordHash());

        return new LogInResponse(user.getId(), user.getUsername(), user.getEmail());
    }
}
