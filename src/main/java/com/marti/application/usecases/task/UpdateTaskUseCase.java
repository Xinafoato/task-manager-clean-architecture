package com.marti.application.usecases.task;

import com.marti.application.dtos.task.UpdateTaskRequest;
import com.marti.domain.model.Task;
import com.marti.domain.service.DomainController;

public class UpdateTaskUseCase {

    private final DomainController domainController;

    public UpdateTaskUseCase(DomainController domainController) {
        this.domainController = domainController;
    }

    public void execute(UpdateTaskRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Request cannot be null");
        }

        domainController.updateTask(
            request.getTaskListId(),
            request.getTaskId(),
            request.getNewTitle(),
            request.getNewDescription(),
            request.getNewPriority(),
            request.getNewDueDate()
        );
    }
}
