package com.marti.application.dtos.taskList;

public class TaskListDTO {

    private final String id;
    private final String name;
    private final String userId;

    public TaskListDTO(String id, String name, String userId) {
        this.id = id;
        this.name = name;
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUserId() {
        return userId;
    }
}
