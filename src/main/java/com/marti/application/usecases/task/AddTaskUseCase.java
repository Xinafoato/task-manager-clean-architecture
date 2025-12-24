package com.marti.application.usecases.task;

import com.marti.application.dtos.task.AddTaskRequest;
import com.marti.application.dtos.task.TaskDTO;
import com.marti.application.mappers.TaskMapper;
import com.marti.domain.model.Task;
import com.marti.domain.repository.TaskRepository;


public class AddTaskUseCase {

    private final TaskRepository taskRepo;

    public AddTaskUseCase(TaskRepository taskRepo) {
        this.taskRepo = taskRepo;
    }

    public TaskDTO execute(AddTaskRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Request cannot be null");
        }

        Task task = Task.create(request.taskListId(), request.title(), request.description(), request.status(), request.priority(), request.dueDate());

        taskRepo.save(task);

        return TaskMapper.toDTO(task);
    }
}
