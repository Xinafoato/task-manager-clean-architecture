package com.marti.domain.repository;

import com.marti.domain.model.TaskList;

import java.util.List;
import java.util.Optional;

public interface TaskListRepository {

    TaskList save(TaskList taskList);

    Optional<TaskList> findById(String taskListId);

    List<TaskList> findByUserId(String userId);

    boolean existsByNameAndUserId(String name, String userId);

    void deleteById(String taskListId);

    void updateName(String taskListId, String newName);
}
