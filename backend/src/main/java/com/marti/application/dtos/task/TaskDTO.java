package com.marti.application.dtos.task;

import com.marti.domain.model.Priority;
import com.marti.domain.model.Status;

import java.util.Date;

public class TaskDTO {
    private final String id;
    private final String title;
    private final String description;
    private final Status status;
    private final Priority priority;
    private final Date dueDate;

    public TaskDTO(String id, String title, String description, Status status, Priority priority, Date dueDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.dueDate = dueDate;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public Status getStatus() { return status; }
    public Priority getPriority() { return priority; }
    public Date getDueDate() { return dueDate; }
}
