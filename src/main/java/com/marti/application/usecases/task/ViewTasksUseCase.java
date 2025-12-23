package com.marti.application.usecases.task;

import com.marti.application.dtos.task.TaskDTO;
import com.marti.application.mappers.TaskMapper;
import com.marti.domain.model.Task;
import com.marti.domain.service.DomainController;

import java.util.List;

public class ViewTasksUseCase {

    private final DomainController domainController;

    public ViewTasksUseCase(DomainController domainController) {
        this.domainController = domainController;
    }

    public List<TaskDTO> execute(String taskListId) {

        if (taskListId == null || taskListId.isBlank())
            throw new IllegalArgumentException("TaskList ID cannot be empty");

        List<Task> tasks = domainController.getTasks(taskListId);

        return tasks.stream()
                .map(TaskMapper::toDTO)
                .toList();
    }
}
