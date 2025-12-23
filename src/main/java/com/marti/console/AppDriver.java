package com.marti.console;

import com.marti.application.dtos.auth.*;
import com.marti.application.dtos.taskList.*;
import com.marti.application.dtos.task.*;
import com.marti.application.usecases.auth.*;
import com.marti.application.usecases.taskList.*;
import com.marti.application.usecases.task.*;

import com.marti.domain.model.Priority;
import com.marti.domain.model.Status;
import com.marti.domain.service.DomainController;


import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class AppDriver {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        DomainController domain = new DomainController();

        // AUTH use cases
        SignUpUseCase signUpUC = new SignUpUseCase(domain);
        LogInUseCase logInUC = new LogInUseCase(domain);
        DeleteAccountUseCase deleteAccUC = new DeleteAccountUseCase(domain);

        // TASK LIST use cases
        CreateTaskListUseCase createListUC = new CreateTaskListUseCase(domain);
        ViewTaskListsUseCase viewListsUC = new ViewTaskListsUseCase(domain);
        RenameTaskListUseCase renameListUC = new RenameTaskListUseCase(domain);
        DeleteTaskListUseCase deleteListUC = new DeleteTaskListUseCase(domain);

        // TASK use cases
        AddTaskUseCase addTaskUC = new AddTaskUseCase(domain);
        ViewTasksUseCase viewTasksUC = new ViewTasksUseCase(domain);
        DeleteTaskUseCase deleteTaskUC = new DeleteTaskUseCase(domain);
        UpdateTaskUseCase updateTaskUC = new UpdateTaskUseCase(domain);

        String loggedUserId = null;

        // ============== MAIN LOOP ==============
        while (true) {
            if (loggedUserId == null) {
                loggedUserId = authMenu(signUpUC, logInUC);
            } else {
                taskListMenu(
                        loggedUserId,
                        createListUC,
                        viewListsUC,
                        renameListUC,
                        deleteListUC,
                        addTaskUC,
                        viewTasksUC,
                        deleteTaskUC,
                        updateTaskUC
                );
            }
        }
    }

    // ------------------ AUTH MENU ------------------

    private static String authMenu(SignUpUseCase signUpUC, LogInUseCase logInUC) {
        System.out.println("\n===== AUTH MENU =====");
        System.out.println("1. Sign Up");
        System.out.println("2. Log In");
        System.out.println("0. Exit");
        System.out.print("Choose option: ");

        int opt = Integer.parseInt(scanner.nextLine());

        switch (opt) {
            case 1 -> {
                System.out.print("Username: ");
                String u = scanner.nextLine();
                System.out.print("Email: ");
                String e = scanner.nextLine();
                System.out.print("Password: ");
                String p = scanner.nextLine();

                SignUpResponse response = signUpUC.execute(new SignUpRequest(u, e, p));
                System.out.println("User created! Welcome " + response.getUserInfo().getUsername());
                return response.getUserInfo().getUserId();
            }
            case 2 -> {
                System.out.print("Username: ");
                String username = scanner.nextLine();
                System.out.print("Email: ");
                String email = scanner.nextLine();
                System.out.print("Password: ");
                String pass = scanner.nextLine();

                LogInResponse res = logInUC.execute(new LogInRequest(username,email, pass));
                System.out.println("Logged in as: " + res.getUserInfo().getUsername());
                return res.getUserInfo().getUserId();
            }
            case 0 -> System.exit(0);
            default -> System.out.println("Invalid option!");
        }
        return null;
    }

    // ------------------ TASK LIST MENU ------------------

    private static void taskListMenu(
            String userId,
            CreateTaskListUseCase createUC,
            ViewTaskListsUseCase viewUC,
            RenameTaskListUseCase renameUC,
            DeleteTaskListUseCase deleteUC,
            AddTaskUseCase addTaskUC,
            ViewTasksUseCase viewTasksUC,
            DeleteTaskUseCase deleteTaskUC,
            UpdateTaskUseCase updateTaskUC
    ) {
        while (true) {
            System.out.println("\n===== TASK LIST MENU =====");
            System.out.println("1. View Task Lists");
            System.out.println("2. Create Task List");
            System.out.println("3. Rename Task List");
            System.out.println("4. Delete Task List");
            System.out.println("5. Enter Task List");
            System.out.println("0. Log Out");

            System.out.print("Choose option: ");
            int opt = Integer.parseInt(scanner.nextLine());

            switch (opt) {
                case 1 -> {
                    List<TaskListDTO> lists = viewUC.execute(userId);
                    System.out.println("\nYour Lists:");
                    for (TaskListDTO l : lists) {
                        System.out.println(" - " + l.getId() + " : " + l.getName());
                    }
                }
                case 2 -> {
                    System.out.print("List name: ");
                    String name = scanner.nextLine();
                    createUC.execute(name, userId);
                    System.out.println("List created!");
                }
                case 3 -> {
                    System.out.print("List ID: ");
                    String id = scanner.nextLine();
                    System.out.print("New name: ");
                    String name = scanner.nextLine();
                    renameUC.execute(id, name);
                }
                case 4 -> {
                    System.out.print("List ID: ");
                    String id = scanner.nextLine();
                    deleteUC.execute(id);
                }
                case 5 -> {
                    System.out.print("List ID: ");
                    String id = scanner.nextLine();
                    taskMenu(id, addTaskUC, viewTasksUC, deleteTaskUC, updateTaskUC);
                }
                case 0 -> {
                    return; // log out
                }
                default -> System.out.println("Invalid option!");
            }
        }
    }

    // ------------------ TASK MENU ------------------

    private static void taskMenu(
            String listId,
            AddTaskUseCase addTaskUC,
            ViewTasksUseCase viewTasksUC,
            DeleteTaskUseCase deleteTaskUC,
            UpdateTaskUseCase updateTaskUC
    ) {
        while (true) {
            System.out.println("\n===== TASK MENU =====");
            System.out.println("1. View Tasks");
            System.out.println("2. Add Task");
            System.out.println("3. Delete Task");
            System.out.println("4. Update Task");
            System.out.println("0. Back");

            System.out.print("Choose option: ");
            int opt = Integer.parseInt(scanner.nextLine());

            switch (opt) {
                case 1 -> {
                    List<TaskDTO> tasks = viewTasksUC.execute(listId);
                    System.out.println("Tasks:");
                    for (TaskDTO t : tasks) {
                        System.out.println(" - " + t.getId() + " | " + t.getTitle() + " | Priority: " + t.getPriority());
                    }
                }
                case 2 -> {
                    System.out.print("Title: ");
                    String title = scanner.nextLine();
                    System.out.print("Description: ");
                    String desc = scanner.nextLine();
                    System.out.print("Priority (LOW, MEDIUM, HIGH) [default MEDIUM]: ");
                    String pr = scanner.nextLine();
                    Priority priority = pr.isBlank() ? Priority.MEDIUM : Priority.valueOf(pr.toUpperCase());

                    System.out.print("Status (PENDING, IN_PROGRESS, DONE) [default TODO]: ");
                    String st = scanner.nextLine();
                    Status status = st.isBlank() ? Status.PENDING : Status.valueOf(st.toUpperCase());

                    System.out.print("Due date (yyyy-mm-dd, empty for none): ");
                    String dateStr = scanner.nextLine();
                    Date dueDate = null;
                    if (!dateStr.isBlank()) {
                        dueDate = java.sql.Date.valueOf(dateStr);
                    }


                    addTaskUC.execute(new AddTaskRequest(
                            listId,
                            title,
                            desc,
                            status,
                            dueDate,
                            priority
                    ));

                    System.out.println("Task added!");
                }
                case 3 -> {
                    System.out.print("Task ID: ");
                    String id = scanner.nextLine();
                    deleteTaskUC.execute(listId, id);
                }
                case 4 -> {
                    System.out.print("Task ID: ");
                    String id = scanner.nextLine();
                    System.out.print("New Title (or empty): ");
                    String t = scanner.nextLine();
                    System.out.print("New Description (or empty): ");
                    String d = scanner.nextLine();

                    updateTaskUC.execute(new UpdateTaskRequest(
                            listId,
                            id,
                            t.isBlank() ? null : t,
                            d.isBlank() ? null : d,
                            null,
                            null
                    ));

                    System.out.println("Task updated!");
                }
                case 0 -> {
                    return;
                }
                default -> System.out.println("Invalid option!");
            }
        }
    }
}
