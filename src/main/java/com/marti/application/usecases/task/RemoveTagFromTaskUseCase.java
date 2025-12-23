package com.marti.application.usecases.task;


import com.marti.domain.model.Task;
import com.marti.domain.service.DomainController;

public class RemoveTagFromTaskUseCase {

    private final DomainController domainController;

    public RemoveTagFromTaskUseCase(DomainController domainController) {
        this.domainController = domainController;
    }

    public void execute(String taskListId, String taskId, String tag) {
        if (taskListId == null || taskListId.isBlank()) {
            throw new IllegalArgumentException("TaskList ID cannot be empty");
        }
        if (taskId == null || taskId.isBlank()) {
            throw new IllegalArgumentException("Task ID cannot be empty");
        }
        if (tag == null || tag.isBlank()) {
            throw new IllegalArgumentException("Tag cannot be empty");
        }

        domainController.removeTagFromTask(taskListId, taskId, tag);
    }
}
