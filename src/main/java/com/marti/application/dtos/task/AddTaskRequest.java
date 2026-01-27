package com.marti.application.dtos.task;

import com.marti.domain.model.Priority;
import com.marti.domain.model.Status;

import java.util.Date;

public record AddTaskRequest(
        String taskListId,
        String title,
        String description,
        String status,
        Date dueDate,
        String priority,
        String userId
) {}
