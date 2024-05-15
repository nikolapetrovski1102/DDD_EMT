package com.example.taskmanagement.repository;

import com.example.taskmanagement.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String usernameOrEmail);

    User findByUsername(String username);

}