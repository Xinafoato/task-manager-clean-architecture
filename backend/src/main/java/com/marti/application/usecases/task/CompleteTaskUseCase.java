package com.marti.application.usecases.task;

import com.marti.domain.model.Task;
import com.marti.domain.repository.TaskRepository;

public class CompleteTaskUseCase {

    private final TaskRepository taskRepo;

    public CompleteTaskUseCase(TaskRepository taskRepo) {
        this.taskRepo = taskRepo;
    }

    public void execute(String taskListId, String taskId) {

        Task task = taskRepo.findById(taskId).orElseThrow(() -> new IllegalArgumentException("Task not found"));

        if (!task.getTaskListId().equals(taskListId)) {
            throw new IllegalArgumentException("Task does not belong to this task list");
        }

        task.markAsDone(); //IN_PROGRESS --> DONE

        taskRepo.save(task);
    }
}

