package com.marti.domain.model;

import java.util.Date;

public class Task {
    private String id;
    private String title;
    private String description;
    private Status status;
    private Priority priority;
    private Date dueDate;
    private Date createdAt;
    private Date updatedAt;

    public Task(String id , String title, String description, Status status, Priority priority, Date dueDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.dueDate = dueDate;
        this.createdAt = new Date();
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
    public void setStatus(Status status) {
        this.status = status;
        this.updatedAt = new Date();
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
    public void markAsDone(){
        this.status = Status.DONE;
        this.updatedAt = new Date();
    }
}
