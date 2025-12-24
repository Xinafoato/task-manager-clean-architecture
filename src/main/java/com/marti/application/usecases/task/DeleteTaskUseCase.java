package com.marti.application.usecases.task;

import com.marti.domain.model.Task;
import com.marti.domain.repository.TaskRepository;
import com.marti.domain.service.DomainController;

public class DeleteTaskUseCase {

    private final TaskRepository taskRepo;

    public DeleteTaskUseCase(TaskRepository taskRepo) {

        this.taskRepo = taskRepo;
    }

    public void execute(String taskListId, String taskId) {

        if (taskListId == null || taskListId.isBlank())
            throw new IllegalArgumentException("TaskList ID cannot be empty");

        if (taskId == null || taskId.isBlank()) throw new IllegalArgumentException("Task ID cannot be empty");

        Task task = taskRepo.findById(taskId).orElseThrow(() -> new IllegalArgumentException("Task not found"));

        if (!task.getTaskListId().equals(taskListId)) {
            throw new IllegalArgumentException("Task does not belong to the given task list");
        }

        taskRepo.deleteById(taskId);
    }
}
