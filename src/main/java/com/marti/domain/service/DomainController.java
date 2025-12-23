package com.marti.domain.service;

import com.marti.domain.model.*;

import java.util.*;
import java.util.stream.Collectors;

public class DomainController {

    private final Map<String, User> users = new HashMap<>();
    private final Map<String, TaskList> taskLists = new HashMap<>();
    private final Map<String, Tag> tags = new HashMap<>();

    // ============================
    //        AUTHENTICATION
    // ============================
    public User signUp(String username, String email, String passwordHash) {
        if (users.values().stream().anyMatch(u -> u.getEmail().equalsIgnoreCase(email))) {
            throw new IllegalArgumentException("Email already registered");
        }

        User user = new User(username, email, passwordHash);
        users.put(user.getId(), user);
        return user;
    }

    public User login(String email, String passwordHash) {
        return users.values().stream().filter(u -> u.getEmail().equalsIgnoreCase(email) && u.getPasswordHash().equals(passwordHash)).findFirst().orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));
    }

    public void deleteAccount(String userId) {
        users.remove(userId);

        taskLists.values().removeIf(tl -> tl.getUserId().equals(userId));

        tags.values().removeIf(tag -> tag.getUser_id().equals(userId));
    }

    // ============================
    //         TASK LISTS
    // ============================

    public TaskList createTaskList(String name, String userId) {
        if (!users.containsKey(userId)) {
            throw new IllegalArgumentException("User does not exist");
        }

        TaskList list = new TaskList(null, name, userId);
        taskLists.put(list.getId(), list);
        return list;
    }

    public List<TaskList> getTaskLists(String userId) {
        return taskLists.values().stream().filter(tl -> tl.getUserId().equals(userId)).collect(Collectors.toList());
    }

    public void renameTaskList(String listId, String newName) {
        TaskList list = getTaskListOrThrow(listId);
        list.setName(newName);
    }

    public void deleteTaskList(String listId) {
        taskLists.remove(listId);
    }

    private TaskList getTaskListOrThrow(String id) {
        TaskList list = taskLists.get(id);
        if (list == null) {
            throw new IllegalArgumentException("TaskList not found");
        }
        return list;
    }

    // ============================
    //           TASKS
    // ============================

    public Task addTask(String taskListId, String title, String description, Status status, Priority priority, Date dueDate) {

        TaskList list = getTaskListOrThrow(taskListId);

        Task task = new Task(null, title, description, status, priority, dueDate);
        list.addTask(task);
        return task;
    }

    public List<Task> getTasks(String taskListId) {
        return getTaskListOrThrow(taskListId).getTasks();
    }

    public void deleteTask(String taskListId, String taskId) {
        TaskList list = getTaskListOrThrow(taskListId);
        Task task = list.findTaskById(taskId);
        if (task != null) list.removeTask(task);
    }

    public void updateTask(String taskListId, String taskId, String newTitle, String newDescription, Priority newPriority, Date newDueDate) {

        TaskList list = getTaskListOrThrow(taskListId);
        Task task = list.findTaskById(taskId);
        if (task == null) throw new IllegalArgumentException("Task not found");

        if (newTitle != null) task.setTitle(newTitle);
        if (newDescription != null) task.setDescription(newDescription);
        if (newPriority != null) task.setPriority(newPriority);
        if (newDueDate != null) task.setDueDate(newDueDate);
    }

    // ============================
    //            TAGS
    // ============================

    public Tag createTag(String name, String userId) {
        Tag tag = new Tag(null, name, userId);
        tags.put(tag.getId(), tag);
        return tag;
    }

    public List<Tag> getTagsForUser(String userId) {
        return tags.values().stream().filter(t -> t.getUser_id().equals(userId)).collect(Collectors.toList());
    }

    public void addTagToTask(String taskListId, String taskId, String tagId) {
        Task task = getTaskListOrThrow(taskListId).findTaskById(taskId);
        Tag tag = tags.get(tagId);

        if (task == null || tag == null) {
            throw new IllegalArgumentException("Task or Tag not found");
        }
        task.setDescription(task.getDescription() + " [tag:" + tag.getName() + "]");
    }

    public void removeTagFromTask(String taskListId, String taskId, String tagId) {
        Task task = getTaskListOrThrow(taskListId).findTaskById(taskId);
        Tag tag = tags.get(tagId);

        if (task == null || tag == null) {
            throw new IllegalArgumentException("Task or Tag not found");
        }

        task.setDescription(task.getDescription().replace("[tag:" + tag.getName() + "]", ""));
    }

    // ============================
    //          FILTERS
    // ============================

    public List<Task> filterByTag(String taskListId, String tagName) {
        return getTasks(taskListId).stream().filter(t -> t.getDescription().contains("[tag:" + tagName + "]")).collect(Collectors.toList());
    }

    public List<Task> filterByPriority(String taskListId, Priority p) {
        return getTasks(taskListId).stream().filter(t -> t.getPriority() == p).collect(Collectors.toList());
    }

    public List<Task> filterByDueDate(String taskListId, Date date) {
        return getTasks(taskListId).stream().filter(t -> t.getDueDate() != null && t.getDueDate().equals(date)).collect(Collectors.toList());
    }

    public List<Task> sortTasks(String taskListId, Comparator<Task> comparator) {
        return getTasks(taskListId).stream().sorted(comparator).collect(Collectors.toList());
    }
}
