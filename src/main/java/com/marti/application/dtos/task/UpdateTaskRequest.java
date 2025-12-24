package com.marti.application.dtos.task;

import com.marti.domain.model.Priority;

import java.util.Date;

public class UpdateTaskRequest {

    private final String taskListId;
    private final String taskId;
    private final String newTitle;
    private final String newDescription;
    private final String newPriority;
    private final Date newDueDate;

    public UpdateTaskRequest(String taskListId, String taskId, String newTitle, String newDescription, String newPriority, Date newDueDate) {

        if (taskListId == null || taskListId.isBlank()) {
            throw new IllegalArgumentException("TaskList ID cannot be empty");
        }

        if (taskId == null || taskId.isBlank()) {
            throw new IllegalArgumentException("Task ID cannot be empty");
        }

        this.taskListId = taskListId;
        this.taskId = taskId;
        this.newTitle = newTitle;
        this.newDescription = newDescription;
        this.newPriority = newPriority;
        this.newDueDate = newDueDate;
    }

    public String getTaskListId() {
        return taskListId;
    }

    public String getTaskId() {
        return taskId;
    }

    public String getNewTitle() {
        return newTitle;
    }

    public String getNewDescription() {
        return newDescription;
    }

    public String getNewPriority() {
        return newPriority;
    }

    public Date getNewDueDate() {
        return newDueDate;
    }
}
