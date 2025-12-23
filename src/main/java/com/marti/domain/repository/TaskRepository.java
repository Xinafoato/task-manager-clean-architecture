package com.marti.domain.repository;

import com.marti.domain.model.Task;

import java.util.List;
import java.util.Optional;

public interface TaskRepository {

    Task save(Task task);

    Optional<Task> findById(String taskId);

    List<Task> findByTaskListId(String taskListId);

    List<Task> findByUserId(String userId);

    void deleteById(String taskId);
}
// NOTE:
// We intentionally do not include advanced filters (status, priority, tags, etc.) at this stage.
// The repository exposes only the operations required by current use cases.
// Additional query methods will be introduced later when the domain requirements justify them.