package com.marti.application.usecases.taskList;

import com.marti.domain.service.DomainController;

public class DeleteTaskListUseCase {

    private final DomainController domainController;

    public DeleteTaskListUseCase(DomainController domainController) {
        this.domainController = domainController;
    }

    public void execute(String taskListId) {

        if (taskListId == null || taskListId.isBlank()) {
            throw new IllegalArgumentException("TaskList ID cannot be empty");
        }

        domainController.deleteTaskList(taskListId);
    }
}

