package com.marti.console.ui;

import com.marti.application.dtos.task.AddTaskRequest;
import com.marti.application.dtos.task.TaskDTO;
import com.marti.application.usecases.task.AddTaskUseCase;
import com.marti.application.usecases.task.CompleteTaskUseCase;
import com.marti.application.usecases.task.ViewTasksUseCase;

import java.util.List;
import java.util.Scanner;

public class ConsoleUI {

    private static final String DEFAULT_USER_ID = "user-1";
    private static final String DEFAULT_TASK_LIST_ID = "default";

    private final AddTaskUseCase addTaskUC;
    private final ViewTasksUseCase viewTasksUC;
    private final CompleteTaskUseCase completeTaskUC;

    public ConsoleUI(
            AddTaskUseCase addTaskUC,
            ViewTasksUseCase viewTasksUC,
            CompleteTaskUseCase completeTaskUC
    ) {
        this.addTaskUC = addTaskUC;
        this.viewTasksUC = viewTasksUC;
        this.completeTaskUC = completeTaskUC;
    }

    public void start() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            printMenu();
            String option = sc.nextLine();

            switch (option) {
                case "1" -> addTask(sc);
                case "2" -> viewTasks();
                case "3" -> completeTask(sc);
                case "0" -> {
                    System.out.println("Bye 👋");
                    return;
                }
                default -> System.out.println("Invalid option ❌");
            }
        }
    }

    private void printMenu() {
        System.out.println("""
                
                ===== TASK MANAGER =====
                1. Add task
                2. View tasks
                3. Complete task
                0. Exit
                ------------------------
                Choose an option:
                """);
    }

    private void addTask(Scanner sc) {
        System.out.print("Title: ");
        String title = sc.nextLine();

        System.out.print("Description (optional): ");
        String description = sc.nextLine();

        AddTaskRequest request = new AddTaskRequest(
                DEFAULT_TASK_LIST_ID,
                title,
                description,
                null,
                null,
                null,
                DEFAULT_USER_ID
        );

        addTaskUC.execute(request);
        System.out.println("✅ Task added");
    }

    private void viewTasks() {
        List<TaskDTO> tasks = viewTasksUC.execute(DEFAULT_TASK_LIST_ID);

        if (tasks.isEmpty()) {
            System.out.println("No tasks yet 💤");
            return;
        }

        System.out.println("\nYour tasks:");
        tasks.forEach(task ->
                System.out.println(
                        task.getId() + " | " +
                                task.getTitle() + " | " +
                                task.getStatus()
                )
        );
    }

    private void completeTask(Scanner sc) {
        System.out.print("Task ID to complete: ");
        String taskId = sc.nextLine();

        completeTaskUC.execute(DEFAULT_TASK_LIST_ID, taskId);
        System.out.println("✅ Task completed");
    }
}
