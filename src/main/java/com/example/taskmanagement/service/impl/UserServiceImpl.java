package com.example.taskmanagement.service.impl;

import com.example.taskmanagement.models.User;
import com.example.taskmanagement.models.UserRole;
import com.example.taskmanagement.models.exceptions.UserNotFoundException;
import com.example.taskmanagement.repository.UserRepository;
import com.example.taskmanagement.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void register(User user) {
        User newUser = new User(user.getUsername(), user.getEmail(), user.getPassword(), UserRole.USER_ROLE);
        userRepository.save(newUser);
    }

    @Override
    public User getUserById(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        return optionalUser.orElseThrow(UserNotFoundException::new);
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void forgotPassword(Long userId, String password) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setPassword(password);
            userRepository.save(user);
        }
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public boolean isUsernameAvailable(String username) {
        User user = userRepository.findByUsername(username);
        return user == null;
    }

    @Override
    public boolean authenticateUser(String usernameOrEmail, String password) {
        User user = userRepository.findByUsername(usernameOrEmail);

        if (Objects.isNull(user)){
            user = userRepository.findByEmail(usernameOrEmail);
        }

        return user != null && user.getPassword().equals(password);
    }
}
