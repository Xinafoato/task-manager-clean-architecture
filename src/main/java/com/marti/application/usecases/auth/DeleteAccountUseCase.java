package com.marti.application.usecases.auth;

import com.marti.domain.service.DomainController;

public class DeleteAccountUseCase {
    private final DomainController domainController;

    public DeleteAccountUseCase(DomainController domainController) {
        this.domainController = domainController;
    }

    public void execute(String userId) {
        if (userId == null || userId.isBlank()) {
            throw new IllegalArgumentException("User ID cannot be null or empty");
        }

        domainController.deleteAccount(userId);

    }
}
