package com.marti.application.usecases.taskList;

import com.marti.application.dtos.taskList.TaskListDTO;
import com.marti.application.mappers.TaskListMapper;
import com.marti.domain.model.TaskList;
import com.marti.domain.repository.TaskListRepository;


import java.util.List;

public class ViewTaskListsUseCase {

    private final TaskListRepository taskListRepo;

    public ViewTaskListsUseCase(TaskListRepository taskListRepo) {
        this.taskListRepo = taskListRepo;
    }

    public List<TaskListDTO> execute(String userId) {

        if (userId == null || userId.isBlank()) {
            throw new IllegalArgumentException("User ID cannot be empty");
        }

        List<TaskList> lists = taskListRepo.findByUserId(userId);

        return lists.stream().map(TaskListMapper::toDTO).toList();
    }
}
