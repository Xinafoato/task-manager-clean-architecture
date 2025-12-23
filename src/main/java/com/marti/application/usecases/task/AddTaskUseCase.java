package com.marti.application.usecases.task;

import com.marti.application.dtos.task.AddTaskRequest;
import com.marti.domain.model.Task;
import com.marti.domain.service.DomainController;

public class AddTaskUseCase {

    private final DomainController domainController;

    public AddTaskUseCase(DomainController domainController) {
        this.domainController = domainController;
    }

    public Task execute(AddTaskRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Request cannot be null");
        }

        return domainController.addTask(
                request.taskListId(),
                request.title(),
                request.description(),
                request.status(),
                request.priority(),
                request.dueDate()
        );
    }
}
