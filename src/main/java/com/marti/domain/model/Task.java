package com.marti.domain.model;

import java.util.Date;
import java.util.UUID;

public class Task {
    private String userId;
    private String id;
    private String title;
    private String description;
    private Status status;
    private Priority priority;
    private Date dueDate;
    private Date createdAt;
    private Date updatedAt;
    private String taskListId;

    private Task(String id ,String taskListId, String title, String description, Status status, Priority priority, Date dueDate, String userId) {
        this.id = id != null? id: UUID.randomUUID().toString();
        this.taskListId = taskListId;
        this.title = title;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.dueDate = dueDate;
        this.createdAt = new Date();
        this.updatedAt = new Date();
        this.userId = userId;
    }

    //Factory Method
    public static Task create(
            String taskListId,
            String title,
            String description,
            Status status,
            Priority priority,
            Date dueDate,
            String userId
    ) {
        if (taskListId == null || taskListId.isBlank()) {
            throw new IllegalArgumentException("TaskList ID cannot be null or empty");
        }

        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Task title cannot be null or empty");
        }

        return new Task(
                null, // id → domain generates
                taskListId,
                title,
                description,
                status != null ? status : Status.TODO,
                priority != null ? priority : Priority.MEDIUM,
                dueDate,
                userId
        );
    }

    public static Task reconstruct(String id, String taskListId, String title, String description, Status status, Priority priority, Date dueDate, Date createdAt, Date updatedAt, String userId) {
        Task task = new Task(id, taskListId, title, description, status, priority, dueDate, userId);
        task.createdAt = createdAt;
        task.updatedAt = updatedAt;
        return task;
    }

    public void update(
            String newTitle,
            String newDescription,
            Priority newPriority,
            Date newDueDate
    ) {
        if (newTitle != null && !newTitle.isBlank()) {
            this.title = newTitle;
        }

        if (newDescription != null) {
            this.description = newDescription;
        }

        if (newPriority != null) {
            this.priority = newPriority;
        }

        if (newDueDate != null) {
            this.dueDate = newDueDate;
        }

        this.updatedAt = new Date();
    }



    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
        this.updatedAt = new Date();
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
        this.updatedAt = new Date();
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
        this.updatedAt = new Date();
    }
    public Status getStatus() {
        return status;
    }

    public Priority getPriority() {
        return priority;
    }
    public void setPriority(Priority priority) {
        this.priority = priority;
        this.updatedAt = new Date();
    }
    public Date getDueDate() {
        return dueDate;
    }
    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
        this.updatedAt = new Date();
    }
    public Date getCreatedAt() {
        return createdAt;
    }
    public Date getUpdatedAt() {
        return updatedAt;
    }
    public String getTaskListId() {
        return taskListId;
    }
    public void setTaskListId(String taskListId) {
        this.taskListId = taskListId;
    }

    public void markAsDone(){
        this.status = Status.DONE;
        this.updatedAt = new Date();
    }
    public void start() {
        if (this.status != Status.TODO) {
            throw new IllegalStateException("Task can only be started from TODO");
        }
        this.status = Status.IN_PROGRESS;
        this.updatedAt = new Date();
    }
    public String getUserId() {
        return userId;
    }
}
