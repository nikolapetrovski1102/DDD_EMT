package com.example.taskmanagement.models.dto;

import com.example.taskmanagement.models.TaskStatus;
import com.example.taskmanagement.models.valueobjects.DueDate;
import com.example.taskmanagement.models.valueobjects.TaskDescription;
import com.example.taskmanagement.models.valueobjects.TaskPriority;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskDTO {
    private Long taskId;
    private String title;
    private TaskStatus status;
    private Long userId;
    private TaskPriority taskPriority;
    private LocalDateTime dueDate;
    private String description;
    private LocalDateTime TaskCreated;

    public TaskDTO(Long taskId, String title, TaskStatus status, Long userId, TaskPriority taskPriority, LocalDateTime dueDate, String description) {
        this.taskId = taskId;
        this.title = title;
        this.status = status;
        this.userId = userId;
        this.taskPriority = taskPriority;
        this.dueDate = dueDate;
        this.description = description;
        this.TaskCreated = LocalDateTime.now();
    }
}
