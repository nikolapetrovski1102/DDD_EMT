package com.example.taskmanagement.models;

import com.example.taskmanagement.models.valueobjects.DueDate;
import com.example.taskmanagement.models.valueobjects.TaskDescription;
import com.example.taskmanagement.models.valueobjects.TaskPriority;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Entity
@Data
@NoArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long taskId;
    private String title;
    @Enumerated(EnumType.STRING)
    private TaskStatus status;
    @ManyToOne
    @JoinColumn(nullable = true, name = "user_id")
    private User user;
    private TaskPriority taskPriority;
    @Column(nullable = true)
    private DueDate date;
    @Column(nullable = true)
    private TaskDescription description;
    private LocalDateTime TaskCreated;
    @ManyToOne
    @JoinColumn(name = "subtask_id")
    private TaskList subtask;


    public Task(String title, TaskStatus status, User user, TaskPriority taskPriority, DueDate date, TaskDescription description) {
        this.title = title;
        this.status = status;
        this.user = user;
        this.taskPriority = taskPriority;
        this.date = date;
        this.description = description;
        this.TaskCreated = LocalDateTime.now();
    }
}