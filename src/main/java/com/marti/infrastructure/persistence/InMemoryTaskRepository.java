package com.marti.infrastructure.persistence;
import com.marti.domain.model.Task;
import com.marti.domain.repository.TaskRepository;

import java.util.*;
import java.util.stream.Collectors;

public class InMemoryTaskRepository implements TaskRepository {
    private final Map<String, Task> storage = new HashMap<>();

    @Override
    public void save(Task task) {
        storage.put(task.getId(), task);
    }

    @Override
    public Optional<Task> findById(String id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Task> findByTaskListId(String taskListId) {
        return storage.values().stream()
                .filter(t -> t.getTaskListId().equals(taskListId))
                .toList();
    }

    @Override
    public List<Task> findByUserId(String userId) {
        return storage.values().stream()
                .filter(task -> task.getUserId().equals(userId))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(String id) {
        storage.remove(id);
    }
}
