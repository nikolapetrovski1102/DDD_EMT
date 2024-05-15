package com.example.taskmanagement.service.impl;

import com.example.taskmanagement.models.Task;
import com.example.taskmanagement.models.TaskStatus;
import com.example.taskmanagement.models.User;
import com.example.taskmanagement.models.exceptions.TaskNotFoundException;
import com.example.taskmanagement.models.valueobjects.DueDate;
import com.example.taskmanagement.models.valueobjects.TaskDescription;
import com.example.taskmanagement.models.valueobjects.TaskPriority;
import com.example.taskmanagement.repository.TaskRepository;
import com.example.taskmanagement.repository.UserRepository;
import com.example.taskmanagement.service.TaskService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskServiceImpl(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Task createTask(String title, String description, LocalDateTime dueDate, TaskStatus status, Long userId, TaskPriority priority) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));

        TaskDescription taskDescription = new TaskDescription(description);
        DueDate date = new DueDate(dueDate);

        Task task = new Task(title, status, user, priority, date, taskDescription);

        return taskRepository.save(task);
    }

    @Override
    public Task getTaskById(Long taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task not found with ID: " + taskId));
    }

    @Override
    public Task getTasksByUserId(Long userId) {
        return taskRepository.findById(userId).orElseThrow(TaskNotFoundException::new);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @Override
    public void updateTask(Long taskId, String title, String description, LocalDateTime dueDate, TaskStatus status, TaskPriority priority) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task not found with ID: " + taskId));

        TaskDescription taskDescription = new TaskDescription(description);
        DueDate date = new DueDate(dueDate);

        task.setTitle(title);
        task.setDescription(taskDescription);
        task.setDate(date);
        task.setStatus(status);
        task.setTaskPriority(priority);

        taskRepository.save(task);
    }

    @Override
    public void deleteTask(Long taskId) {
        taskRepository.deleteById(taskId);
    }

    @Override
    public void changeTaskStatus(Long taskId, TaskStatus status) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task not found with ID: " + taskId));

        task.setStatus(status);

        taskRepository.save(task);
    }

    @Override
    public void assignTaskToUser(Long taskId, Long userId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task not found with ID: " + taskId));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));

        task.setUser(user);

        taskRepository.save(task);
    }
}
