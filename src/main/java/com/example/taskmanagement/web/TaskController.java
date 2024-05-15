package com.example.taskmanagement.web;

import com.example.taskmanagement.models.Task;
import com.example.taskmanagement.models.dto.TaskDTO;
import com.example.taskmanagement.models.valueobjects.TaskPriority;
import com.example.taskmanagement.models.TaskStatus;
import com.example.taskmanagement.service.TaskService;
import com.example.taskmanagement.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/tasks/")
public class TaskController {

    private final TaskService taskService;
    private final UserService userService;

    public TaskController(TaskService taskService, UserService userService) {
        this.taskService = taskService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody TaskDTO taskDTO) {
        Task task = taskService.createTask(taskDTO.getTitle(), taskDTO.getDescription(),
                taskDTO.getDueDate(), TaskStatus.PENDING, taskDTO.getUserId(), TaskPriority.MEDIUM);
        return new ResponseEntity<>(task, HttpStatus.CREATED);
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long taskId) {
        Task task = taskService.getTaskById(taskId);
        if (task != null) {
            return ResponseEntity.ok(task);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Task>> getTasksByUserId(@PathVariable Long userId) {
        List<Task> tasks = taskService.getTasksByUserId(userId);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        List<Task> tasks = taskService.getAllTasks();
        return ResponseEntity.ok(tasks);
    }

    @PutMapping("edit/{taskId}")
    public ResponseEntity<?> updateTask(@PathVariable Long taskId, @RequestBody TaskDTO taskUpdateDTO) {
        taskService.updateTask(taskId, taskUpdateDTO.getTitle(), taskUpdateDTO.getDescription(),
                taskUpdateDTO.getDueDate(), taskUpdateDTO.getStatus(), taskUpdateDTO.getTaskPriority());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("delete/{taskId}")
    public ResponseEntity<?> deleteTask(@PathVariable Long taskId) {
        taskService.deleteTask(taskId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("changeStatus/{taskId}")
    public ResponseEntity<?> changeTaskStatus(@PathVariable Long taskId, @RequestParam TaskStatus status) {
        taskService.changeTaskStatus(taskId, status);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{taskId}/assign")
    public ResponseEntity<?> assignTaskToUser(@PathVariable Long taskId, @RequestParam Long userId) {
        taskService.assignTaskToUser(taskId, userId);
        return ResponseEntity.ok().build();
    }
}
