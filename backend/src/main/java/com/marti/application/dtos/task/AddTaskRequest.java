package com.marti.application.dtos.task;

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
