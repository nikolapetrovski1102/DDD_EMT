package com.example.taskmanagement.repository;

import com.example.taskmanagement.models.Task;
import com.example.taskmanagement.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findAllByUser (User user);

}
