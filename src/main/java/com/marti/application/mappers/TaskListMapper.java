package com.marti.application.mappers;

import com.marti.application.dtos.taskList.TaskListDTO;
import com.marti.domain.model.TaskList;

public class TaskListMapper {

    public static TaskListDTO toDTO(TaskList taskList) {
        return new TaskListDTO(
                taskList.getId(),
                taskList.getName(),
                taskList.getUserId()
        );
    }
}
