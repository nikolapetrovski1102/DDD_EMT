package com.example.taskmanagement.service;

import com.example.taskmanagement.models.User;

import java.util.List;

public interface UserService {
    void register(User user);
    User getUserById(Long userId);
    User getUserByUsername(String username);
    List<User> getAllUsers();
    void forgotPassword(Long userId, String password);
    void deleteUser(Long userId);
    boolean isUsernameAvailable(String username);
    boolean authenticateUser(String usernameOrEmail, String password);
}