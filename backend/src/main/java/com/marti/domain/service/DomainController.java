package com.marti.domain.service;

import com.marti.domain.model.*;

import java.util.*;
import java.util.stream.Collectors;

public class DomainController {

    private final Map<String, User> users = new HashMap<>();
    private final Map<String, TaskList> taskLists = new HashMap<>();
    private final Map<String, Tag> tags = new HashMap<>();


    // ============================
    //         TASK LISTS
    // ============================



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


    public List<Task> getTasks(String taskListId) {
        return getTaskListOrThrow(taskListId).getTasks();
    }

    public void deleteTask(String taskListId, String taskId) {
        TaskList list = getTaskListOrThrow(taskListId);
        Task task = list.findTaskById(taskId);
        if (task != null) list.removeTask(task);
    }


    // ============================
    //            TAGS
    // ============================



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
