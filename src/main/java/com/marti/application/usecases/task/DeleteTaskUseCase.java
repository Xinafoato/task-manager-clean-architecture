package com.marti.application.usecases.task;

import com.marti.domain.service.DomainController;

public class DeleteTaskUseCase {

    private final DomainController domainController;

    public DeleteTaskUseCase(DomainController domainController) {
        this.domainController = domainController;
    }

    public void execute(String taskListId, String taskId) {

        if (taskListId == null || taskListId.isBlank())
            throw new IllegalArgumentException("TaskList ID cannot be empty");

        if (taskId == null || taskId.isBlank())
            throw new IllegalArgumentException("Task ID cannot be empty");

        domainController.deleteTask(taskListId, taskId);
    }
}
