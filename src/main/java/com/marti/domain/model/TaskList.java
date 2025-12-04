package com.marti.domain.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TaskList {
    private String id;
    private String name;
    private String userId;
    private List<Task> tasks;

    public TaskList(String id, String name, String userId) {
        this.id = id != null ? id : UUID.randomUUID().toString();
        this.name = name;
        this.userId = userId;
        this.tasks = new ArrayList<>();
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public List<Task> getTasks() {
        return tasks;
    }
    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
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
