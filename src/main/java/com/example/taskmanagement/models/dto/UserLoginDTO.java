package com.example.taskmanagement.models.dto;

import lombok.Data;

@Data
public class UserLoginDTO {
    private String usernameOrEmail;
    private String password;

    public UserLoginDTO() {
    }

    public UserLoginDTO(String usernameOrEmail, String password) {
        this.usernameOrEmail = usernameOrEmail;
        this.password = password;
    }
}