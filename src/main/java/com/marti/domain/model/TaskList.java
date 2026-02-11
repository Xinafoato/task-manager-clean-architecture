package com.marti.domain.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TaskList {
    private String id;
    private String name;
    private String userId;
    private List<Task> tasks;

    private TaskList(String id, String name, String userId, List<Task> tasks) {
        this.id = id != null ? id : UUID.randomUUID().toString();
        this.name = name;
        this.userId = userId;
        this.tasks = tasks != null ? tasks : new ArrayList<>();
    }

    //Factory Method
    public static TaskList create(String userId, String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("TaskList name cannot be null or empty");
        }
        if (userId == null || userId.isBlank()) {
            throw new IllegalArgumentException("User ID cannot be null or empty");
        }
        return new TaskList(null, name, userId, null);
    }

    public static TaskList reconstruct (String Id, String userId, String name, List<Task> tasks) {
        return new TaskList(Id,name,userId,tasks);
    }

    public void update(String newName) {
        if (newName != null && !newName.isBlank()) {
            this.name = newName;
        }
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
    public List<Task> getTasks() {
        return tasks;
    }


    public void addTask(Task task){
        this.tasks.add(task);
    }
    public void removeTask(Task task){
        this.tasks.remove(task);
    }
    public boolean existsTask(String id){
        for(Task task : this.tasks){
            if(task.getId().equals(id)){
                return true;
            }
        }
        return false;
    }
    public Task findTaskById(String id){
        for(Task task : tasks){
            if(task.getId().equals(id)){
                return task;
            }
        }
        return null;
    }
}
