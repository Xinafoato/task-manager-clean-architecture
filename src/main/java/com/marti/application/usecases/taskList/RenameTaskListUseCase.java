package com.marti.application.usecases.taskList;

import com.marti.domain.service.DomainController;

public class RenameTaskListUseCase {

    private final DomainController domainController;

    public RenameTaskListUseCase(DomainController domainController) {
        this.domainController = domainController;
    }

    public void execute(String taskListId, String newName) {

        if (taskListId == null || taskListId.isBlank()) {
            throw new IllegalArgumentException("TaskList ID cannot be empty");
        }

        if (newName == null || newName.isBlank()) {
            throw new IllegalArgumentException("New name cannot be empty");
        }

        domainController.renameTaskList(taskListId, newName);
    }
}
