package com.example.taskmanagement.config;

import com.example.taskmanagement.models.Task;
import com.example.taskmanagement.models.TaskStatus;
import com.example.taskmanagement.models.User;
import com.example.taskmanagement.models.UserRole;
import com.example.taskmanagement.models.valueobjects.TaskDescription;
import com.example.taskmanagement.models.valueobjects.TaskPriority;
import com.example.taskmanagement.repository.TaskRepository;
import com.example.taskmanagement.repository.UserRepository;
import jakarta.annotation.Nullable;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.util.Arrays;

@Component
@AllArgsConstructor
public class DataInitializer {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void initData(){
        String encodedPassword = passwordEncoder.encode("user");
        User u1 = new User("user1", "user1@gmail.com", encodedPassword, UserRole.ADMIN_ROLE);
        User u2 = new User("user2", "user2@gmail.com", encodedPassword, UserRole.USER_ROLE);
        TaskDescription taskDescription = new TaskDescription("");
        Task t1 = new Task("title 1", TaskStatus.NOTSTARTED, u1, TaskPriority.LOW, null, taskDescription);
        Task t2 = new Task("title 2", TaskStatus.NOTSTARTED, null, TaskPriority.MEDIUM, null, taskDescription);
        if (userRepository.findAll().isEmpty()) {
            userRepository.saveAll(Arrays.asList(u1,u2));
        }
        if (taskRepository.findAll().isEmpty()){
            taskRepository.saveAll(Arrays.asList(t1, t2));
        }
    }

}
