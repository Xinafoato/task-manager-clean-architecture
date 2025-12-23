package com.marti.application.usecases.taskList;

import com.marti.application.dtos.taskList.TaskListDTO;
import com.marti.application.mappers.TaskListMapper;
import com.marti.domain.model.TaskList;
import com.marti.domain.service.DomainController;

public class CreateTaskListUseCase {

    private final DomainController domainController;

    public CreateTaskListUseCase(DomainController domainController) {
        this.domainController = domainController;
    }

    public TaskListDTO execute(String userId, String name) {

        if (userId == null || userId.isBlank()) {
            throw new IllegalArgumentException("User ID cannot be empty");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("TaskList name cannot be empty");
        }

        TaskList created = domainController.createTaskList(userId, name);

        return TaskListMapper.toDTO(created);
    }
}
