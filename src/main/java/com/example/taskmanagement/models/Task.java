package com.example.taskmanagement.models;

import com.example.taskmanagement.models.valueobjects.DueDate;
import com.example.taskmanagement.models.valueobjects.TaskDescription;
import com.example.taskmanagement.models.valueobjects.TaskPriority;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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
    @JoinColumn(name = "user_id")
    private User user;
    private TaskPriority taskPriority;
    private DueDate date;
    private TaskDescription description;

    public Task(String title, TaskStatus status, User user, TaskPriority taskPriority, DueDate date, TaskDescription description) {
        this.title = title;
        this.status = status;
        this.user = user;
        this.taskPriority = taskPriority;
        this.date = date;
        this.description = description;
    }
}