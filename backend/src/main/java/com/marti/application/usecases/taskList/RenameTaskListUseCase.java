package com.marti.application.usecases.taskList;


import com.marti.domain.repository.TaskListRepository;

public class RenameTaskListUseCase {

    private final TaskListRepository taskListRepo;

    public RenameTaskListUseCase(TaskListRepository taskListRepo) {

        this.taskListRepo = taskListRepo;
    }

    public void execute(String taskListId, String newName) {

        if (taskListId == null || taskListId.isBlank()) {
            throw new IllegalArgumentException("TaskList ID cannot be empty");
        }

        if (newName == null || newName.isBlank()) {
            throw new IllegalArgumentException("New name cannot be empty");
        }


        taskListRepo.findById(taskListId).orElseThrow(() -> new IllegalArgumentException("TaskList Not Found"));

        taskListRepo.updateName(taskListId, newName);
    }
}
