package com.marti.infrastructure.server;

import com.google.gson.Gson;
import com.marti.application.dtos.auth.*;
import com.marti.application.dtos.task.TaskDTO;
import com.marti.application.dtos.task.AddTaskRequest;
import com.marti.application.dtos.task.UpdateTaskRequest;
import com.marti.application.dtos.taskList.TaskListDTO;
import com.marti.application.usecases.auth.*;
import com.marti.application.usecases.task.*;
import com.marti.application.usecases.taskList.*;
import com.marti.domain.repository.TaskRepository;
import com.marti.domain.repository.TaskListRepository;
import com.marti.domain.repository.UserRepository;
import com.marti.domain.service.DomainController;
import com.marti.infrastructure.persistence.*;

import io.javalin.Javalin;

import java.util.List;
import java.util.Map;

public class UnifiedServer {

    public static void main(String[] args) {

        // Inicializar la base de datos
        DatabaseInitializer.init();

        Javalin app = Javalin.create(config -> config.showJavalinBanner = false)
                .start(7000);

        app.before(ctx -> ctx.contentType("application/json"));

        app.exception(Exception.class, (e, ctx) -> {
            e.printStackTrace();
            ctx.status(500).json(Map.of(
                    "title", "Server Error",
                    "status", 500,
                    "details", e.getMessage()
            ));
        });

        Gson gson = new Gson();

        // Repositorios
        TaskRepository taskRepository = new TaskRepositorySQLite();
        TaskListRepository taskListRepository = new TaskListRepositorySQLite();
        UserRepository userRepository = new UserRepositorySQLite();

        DomainController domainController = new DomainController();

        // ====================== CASOS DE USO ======================

        // Task
        AddTaskUseCase addTaskUseCase = new AddTaskUseCase(taskRepository);
        UpdateTaskUseCase updateTaskUseCase = new UpdateTaskUseCase(taskRepository);
        DeleteTaskUseCase deleteTaskUseCase = new DeleteTaskUseCase(taskRepository);
        CompleteTaskUseCase completeTaskUseCase = new CompleteTaskUseCase(taskRepository);
        StartTaskUseCase startTaskUseCase = new StartTaskUseCase(taskRepository);
        ViewTasksUseCase viewTasksUseCase = new ViewTasksUseCase(taskRepository);
        AddTagToTaskUseCase addTagUseCase = new AddTagToTaskUseCase(domainController);
        RemoveTagFromTaskUseCase removeTagUseCase = new RemoveTagFromTaskUseCase(domainController);

        // TaskList
        CreateTaskListUseCase createTaskListUseCase = new CreateTaskListUseCase(taskListRepository);
        DeleteTaskListUseCase deleteTaskListUseCase = new DeleteTaskListUseCase(taskListRepository);
        RenameTaskListUseCase renameTaskListUseCase = new RenameTaskListUseCase(taskListRepository);
        ViewTaskListsUseCase viewTaskListsUseCase = new ViewTaskListsUseCase(taskListRepository);

        // User
        SignUpUseCase signUpUseCase = new SignUpUseCase(userRepository);
        LogInUseCase logInUseCase = new LogInUseCase(userRepository);
        DeleteAccountUseCase deleteAccountUseCase = new DeleteAccountUseCase(userRepository);

        // ====================== ENDPOINTS USERS ======================

        app.post("/users/signup", ctx -> {
            SignUpRequest request = gson.fromJson(ctx.body(), SignUpRequest.class);
            SignUpResponse response = signUpUseCase.execute(request);
            ctx.status(201).json(response);
        });

        app.post("/users/login", ctx -> {
            LogInRequest request = gson.fromJson(ctx.body(), LogInRequest.class);
            LogInResponse response = logInUseCase.execute(request);
            ctx.status(200).json(response);
        });

        app.delete("/users", ctx -> {
            String email = ctx.queryParam("email");
            if (email == null || email.isBlank()) {
                ctx.status(400).result("Email is required");
                return;
            }

            deleteAccountUseCase.executeByEmail(email);
            ctx.status(204);
        });

        // ====================== ENDPOINTS TASKLIST ======================

        app.get("/tasklists", ctx -> {
            String userId = ctx.queryParam("userId");
            if (userId == null || userId.isBlank()) {
                ctx.status(400).result("userId is required");
                return;
            }
            List<TaskListDTO> lists = viewTaskListsUseCase.execute(userId);
            ctx.json(lists);
        });

        app.post("/tasklists", ctx -> {
            TaskListDTO dtoRequest = gson.fromJson(ctx.body(), TaskListDTO.class);
            TaskListDTO dtoResponse = createTaskListUseCase.execute(dtoRequest.getUserId(), dtoRequest.getName());
            ctx.status(201).json(dtoResponse);
        });

        app.put("/tasklists/{taskListId}", ctx -> {
            String taskListId = ctx.pathParam("taskListId");
            TaskListDTO dtoRequest = gson.fromJson(ctx.body(), TaskListDTO.class);
            renameTaskListUseCase.execute(taskListId, dtoRequest.getName());
            ctx.status(204);
        });

        app.delete("/tasklists/{taskListId}", ctx -> {
            String taskListId = ctx.pathParam("taskListId");
            deleteTaskListUseCase.execute(taskListId);
            ctx.status(204);
        });

        // ====================== ENDPOINTS TASK ======================

        app.get("/tasks", ctx -> {
            String taskListId = ctx.queryParam("taskListId");
            if (taskListId == null || taskListId.isBlank()) {
                ctx.status(400).result("taskListId is required");
                return;
            }
            List<TaskDTO> tasks = viewTasksUseCase.execute(taskListId);
            ctx.json(tasks);
        });

        app.post("/tasks", ctx -> {
            AddTaskRequest request = gson.fromJson(ctx.body(), AddTaskRequest.class);
            TaskDTO taskDto = addTaskUseCase.execute(request);
            ctx.status(201).json(taskDto);
        });

        app.put("/tasks/{taskId}", ctx -> {
            String taskId = ctx.pathParam("taskId");
            UpdateTaskRequest request = gson.fromJson(ctx.body(), UpdateTaskRequest.class);
            updateTaskUseCase.execute(request);
            ctx.status(204);
        });

        app.delete("/tasks/{taskId}", ctx -> {
            String taskId = ctx.pathParam("taskId");
            String taskListId = ctx.queryParam("taskListId");
            if (taskListId == null || taskListId.isBlank()) {
                ctx.status(400).result("taskListId is required");
                return;
            }
            deleteTaskUseCase.execute(taskListId, taskId);
            ctx.status(204);
        });

        app.post("/tasks/{taskId}/start", ctx -> {
            String taskId = ctx.pathParam("taskId");
            String taskListId = ctx.queryParam("taskListId");
            startTaskUseCase.execute(taskListId, taskId);
            ctx.status(204);
        });

        app.post("/tasks/{taskId}/complete", ctx -> {
            String taskId = ctx.pathParam("taskId");
            String taskListId = ctx.queryParam("taskListId");
            completeTaskUseCase.execute(taskListId, taskId);
            ctx.status(204);
        });

        app.post("/tasks/{taskId}/tags", ctx -> {
            String taskId = ctx.pathParam("taskId");
            String taskListId = ctx.queryParam("taskListId");
            String tag = ctx.queryParam("tag");
            addTagUseCase.execute(taskListId, taskId, tag);
            ctx.status(204);
        });

        app.delete("/tasks/{taskId}/tags", ctx -> {
            String taskId = ctx.pathParam("taskId");
            String taskListId = ctx.queryParam("taskListId");
            String tag = ctx.queryParam("tag");
            removeTagUseCase.execute(taskListId, taskId, tag);
            ctx.status(204);
        });

        System.out.println("Servidor unificado arrancado en http://localhost:7000");
    }
}
