package com.example.taskmanagement.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class TaskList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long listId;
    private String name;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}