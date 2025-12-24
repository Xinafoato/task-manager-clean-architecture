package com.marti.application.usecases.task;

import com.marti.application.dtos.task.TaskDTO;
import com.marti.application.mappers.TaskMapper;
import com.marti.domain.model.Task;
import com.marti.domain.repository.TaskRepository;


import java.util.List;

public class ViewTasksUseCase {

    private final TaskRepository taskRepo;

    public ViewTasksUseCase(TaskRepository taskRepo) {
        this.taskRepo = taskRepo;
    }

    public List<TaskDTO> execute(String taskListId) {

        if (taskListId == null || taskListId.isBlank())
            throw new IllegalArgumentException("TaskList ID cannot be empty");

        List<Task> tasks = taskRepo.findByTaskListId(taskListId);

        return tasks.stream().map(TaskMapper::toDTO).toList(); //parse task --> taskDTO
    }
}
