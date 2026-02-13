package com.marti.application.usecases.taskList;

import com.marti.application.dtos.taskList.TaskListDTO;
import com.marti.application.mappers.TaskListMapper;
import com.marti.domain.model.TaskList;
import com.marti.domain.repository.TaskListRepository;

public class CreateTaskListUseCase {

    private final TaskListRepository taskListRepo;

    public CreateTaskListUseCase(TaskListRepository taskListRepo) {

        this.taskListRepo = taskListRepo;
    }

    public TaskListDTO execute(String userId, String name) {

        if (userId == null || userId.isBlank()) {
            throw new IllegalArgumentException("User ID cannot be empty");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("TaskList name cannot be empty");
        }

        TaskList taskList = TaskList.create(userId, name);

        taskListRepo.save(taskList);
        return TaskListMapper.toDTO(taskList);
    }
}
