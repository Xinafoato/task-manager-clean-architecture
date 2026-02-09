package com.marti.application.console;

import com.marti.application.usecases.task.AddTaskUseCase;
import com.marti.application.usecases.task.CompleteTaskUseCase;
import com.marti.application.usecases.task.ViewTasksUseCase;
import com.marti.console.ui.ConsoleUI;
import com.marti.domain.repository.TaskRepository;
import com.marti.infrastructure.persistence.InMemoryTaskRepository;

public class AppDriver {

    public static void main(String[] args) {

        // Infrastructure
        TaskRepository taskRepository = new InMemoryTaskRepository();

        // Use cases
        AddTaskUseCase addTaskUC = new AddTaskUseCase(taskRepository);
        ViewTasksUseCase viewTasksUC = new ViewTasksUseCase(taskRepository);
        CompleteTaskUseCase completeTaskUC = new CompleteTaskUseCase(taskRepository);

        // UI
        ConsoleUI ui = new ConsoleUI(
                addTaskUC,
                viewTasksUC,
                completeTaskUC
        );

        ui.start();
    }
}
