package com.marti.application.dtos.task;

import com.marti.domain.model.Priority;
import com.marti.domain.model.Status;

import java.util.Date;

public record AddTaskRequest(
        String taskListId,
        String title,
        String description,
        Status status,
        Date dueDate,
        Priority priority
) {}
