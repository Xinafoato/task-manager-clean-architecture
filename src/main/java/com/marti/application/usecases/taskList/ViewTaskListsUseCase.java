package com.marti.application.usecases.taskList;

import com.marti.application.dtos.taskList.TaskListDTO;
import com.marti.application.mappers.TaskListMapper;
import com.marti.domain.model.TaskList;
import com.marti.domain.service.DomainController;

import java.util.List;

public class ViewTaskListsUseCase {

    private final DomainController domainController;

    public ViewTaskListsUseCase(DomainController domainController) {
        this.domainController = domainController;
    }

    public List<TaskListDTO> execute(String userId) {

        if (userId == null || userId.isBlank()) {
            throw new IllegalArgumentException("User ID cannot be empty");
        }

        List<TaskList> lists = domainController.getTaskLists(userId);

        return lists.stream().map(TaskListMapper::toDTO).toList();
    }
}
