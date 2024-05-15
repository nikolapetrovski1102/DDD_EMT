package com.example.taskmanagement.service.impl;

import com.example.taskmanagement.config.EmailerObject;
import com.example.taskmanagement.event.TaskEvent;
import com.example.taskmanagement.models.Task;
import com.example.taskmanagement.models.TaskStatus;
import com.example.taskmanagement.models.User;
import com.example.taskmanagement.models.exceptions.UserNotFoundException;
import com.example.taskmanagement.models.valueobjects.DueDate;
import com.example.taskmanagement.models.valueobjects.TaskDescription;
import com.example.taskmanagement.models.valueobjects.TaskPriority;
import com.example.taskmanagement.repository.TaskRepository;
import com.example.taskmanagement.repository.UserRepository;
import com.example.taskmanagement.service.TaskService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    public TaskServiceImpl(TaskRepository taskRepository, UserRepository userRepository, ApplicationEventPublisher applicationEventPublisher) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public Task createTask(String title, String description, LocalDateTime dueDate, TaskStatus status, Long userId, TaskPriority priority) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));

        TaskDescription taskDescription = new TaskDescription(description);
        DueDate date = new DueDate(dueDate);

        Task task = new Task(title, status, user, priority, date, taskDescription);

        if (task.getUser() != null) {
            EmailerObject emailer = new EmailerObject("Task " + task.getTitle() + " is created and assinged to you", "Task " + task.getTitle() + " is created assinged to you task priority is " + task.getStatus(), task.getUser().getEmail());
            applicationEventPublisher.publishEvent(new TaskEvent(emailer, "Task " + task.getTitle() + " is created and assinged to you", "Task " + task.getTitle() + " is created assinged to you task priority is " + task.getStatus(), task.getUser().getEmail()));
        }

        return taskRepository.save(task);
    }

    @Override
    public Task getTaskById(Long taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task not found with ID: " + taskId));
    }

    @Override
    public List<Task> getTasksByUserId(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        return taskRepository.findAllByUser(user);
    }

    @Override
    public List<Task> getAllTasks() {
        EmailerObject emailer = new EmailerObject("Getting all tasks", "All tasks fetched" + taskRepository.findAll().stream().toList(), "nikpetrovski007@gmail.com");
        applicationEventPublisher.publishEvent(new TaskEvent(emailer, "Getting all tasks", "All tasks fetched <br/>" + taskRepository.findAll().stream().toList(), "nikpetrovski007@gmail.com"));
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
        Task task = getTaskById(taskId);

        if (task.getUser() != null) {
            EmailerObject emailer = new EmailerObject("Task " + task.getTitle() + " was deleted", "Task " + task.getTitle() + " was deleted", task.getUser().getEmail());
            applicationEventPublisher.publishEvent(new TaskEvent(emailer, "Task " + task.getTitle() + " was deleted", "Task " + task.getTitle() + " was deleted", task.getUser().getEmail()));
        }

        taskRepository.deleteById(taskId);
    }

    @Override
    public void changeTaskStatus(Long taskId, TaskStatus status) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task not found with ID: " + taskId));

        task.setStatus(status);

        if (task.getUser() != null) {
            EmailerObject emailer = new EmailerObject("Task " + task.getTitle() + " changed status", "Task " + task.getTitle() + " changed status to " + task.getStatus(), task.getUser().getEmail());
            applicationEventPublisher.publishEvent(new TaskEvent(emailer, "Task " + task.getTitle() + " changed status", "Task " + task.getTitle() + " changed status to " + task.getStatus(), task.getUser().getEmail()));
        }

        taskRepository.save(task);
    }

    @Override
    public void assignTaskToUser(Long taskId, Long userId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task not found with ID: " + taskId));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));

        task.setUser(user);

        if (task.getUser() != null) {
            EmailerObject emailer = new EmailerObject("Task " + task.getTitle() + " is assinged to you", "Task " + task.getTitle() + " assinged to you " + task.getTitle() + " task priority is " + task.getStatus(), task.getUser().getEmail());
            applicationEventPublisher.publishEvent(new TaskEvent(emailer, "Task " + task.getTitle() + " is assinged to you", "Task " + task.getTitle() + " assinged to you " + task.getTitle() + " task priority is " + task.getStatus(), task.getUser().getEmail()));
        }

        taskRepository.save(task);
    }
}
