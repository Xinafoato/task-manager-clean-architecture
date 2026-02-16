package com.marti.application.usecases.task;

import com.marti.domain.model.Task;
import com.marti.domain.repository.TaskRepository;

import java.util.Optional;

public class ReopenTaskUseCase {
    private final TaskRepository taskRepo;

    public ReopenTaskUseCase(TaskRepository taskRepo) {
        this.taskRepo = taskRepo;
    }

    public void execute(String taskListId, String taskId) {
        Optional<Task> task = taskRepo.findById(taskId);
        if (task.isEmpty()) {
            throw new IllegalArgumentException("Task not found");
        }
        if (!task.get().getTaskListId().equals(taskListId)) {
            throw new IllegalArgumentException("Task does not belong to the specified list");
        }

        task.get().reopen();
        taskRepo.save(task.orElse(null));
    }
}
