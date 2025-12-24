package com.marti.application.usecases.task;

import com.marti.application.dtos.task.UpdateTaskRequest;
import com.marti.domain.model.Priority;
import com.marti.domain.model.Task;
import com.marti.domain.repository.TaskRepository;

public class UpdateTaskUseCase {

    private final TaskRepository taskRepo;

    public UpdateTaskUseCase(TaskRepository taskRepo) {
        this.taskRepo = taskRepo;
    }

    public void execute(UpdateTaskRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Request cannot be null");
        }

        Task task = taskRepo.findById(request.getTaskId()).orElseThrow(() -> new IllegalArgumentException("Task not found"));

        if (!task.getTaskListId().equals(request.getTaskListId())) {
            throw new IllegalArgumentException("Task does not belong to the given TaskList");
        }

        Priority priority;
        try {
            priority = Priority.valueOf(request.getNewPriority().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid priority: " + request.getNewPriority());
        }

        task.update(
                request.getNewTitle(),
                request.getNewDescription(),
                priority,
                request.getNewDueDate()
        );

        taskRepo.save(task);
    }
}
