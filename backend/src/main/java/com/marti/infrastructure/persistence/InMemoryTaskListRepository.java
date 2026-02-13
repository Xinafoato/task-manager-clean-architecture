package com.marti.infrastructure.persistence;

import com.marti.domain.model.TaskList;
import com.marti.domain.repository.TaskListRepository;

import java.util.*;
import java.util.stream.Collectors;

public class InMemoryTaskListRepository implements TaskListRepository {

    private final Map<String, TaskList> storage = new HashMap<>();

    @Override
    public TaskList save(TaskList taskList) {
        storage.put(taskList.getId(), taskList);
        return taskList;
    }

    @Override
    public Optional<TaskList> findById(String taskListId) {
        return Optional.ofNullable(storage.get(taskListId));
    }

    @Override
    public List<TaskList> findByUserId(String userId) {
        return storage.values().stream()
                .filter(list -> list.getUserId().equals(userId))
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsByNameAndUserId(String name, String userId) {
        return storage.values().stream()
                .anyMatch(list ->
                        list.getName().equalsIgnoreCase(name)
                                && list.getUserId().equals(userId)
                );
    }

    @Override
    public void deleteById(String taskListId) {
        storage.remove(taskListId);
    }

    @Override
    public void updateName(String taskListId, String newName) {

        TaskList taskList = storage.get(taskListId);

        if (taskList == null) {
            throw new IllegalArgumentException("Task list not found with id: " + taskListId);
        }

        taskList.update(newName);
    }
}
