package com.example.taskmanagement.models.dto;

import com.example.taskmanagement.models.UserRole;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDTO {
    private Long userId;
    private String username;
    private String email;
    private String password;
    private UserRole userRole;
    private LocalDateTime registrationDate;

    public UserDTO(String username, String email, String password, UserRole userRole) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.userRole = userRole;
        this.registrationDate = LocalDateTime.now();
    }
}
