package com.example.taskmanagement.service;

import com.example.taskmanagement.models.Task;
import com.example.taskmanagement.models.TaskStatus;
import com.example.taskmanagement.models.valueobjects.TaskPriority;

import java.time.LocalDateTime;
import java.util.List;

public interface TaskService {
    Task createTask(String title, String description, LocalDateTime dueDate, TaskStatus status, Long userId, TaskPriority priority);
    Task getTaskById(Long taskId);
    List<Task> getTasksByUserId(Long userId);
    List<Task> getAllTasks();
    void updateTask(Long taskId, String title, String description, LocalDateTime dueDate, TaskStatus status, TaskPriority priority);
    void deleteTask(Long taskId);
    void changeTaskStatus(Long taskId, TaskStatus status);
    void assignTaskToUser(Long taskId, Long userId);
}
