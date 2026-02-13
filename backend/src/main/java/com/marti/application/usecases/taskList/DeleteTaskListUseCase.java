package com.marti.application.usecases.taskList;

import com.marti.domain.model.TaskList;
import com.marti.domain.repository.TaskListRepository;

public class DeleteTaskListUseCase {

    private final TaskListRepository taskListRepo;

    public DeleteTaskListUseCase(TaskListRepository taskListRepo) {

        this.taskListRepo = taskListRepo;
    }

    public void execute(String taskListId) {

        if (taskListId == null || taskListId.isBlank()) {
            throw new IllegalArgumentException("TaskList ID cannot be empty");
        }

        TaskList taskList = taskListRepo.findById(taskListId).orElseThrow(() -> new IllegalArgumentException("TaskList not found"));

       taskListRepo.deleteById(taskListId);
    }
}

