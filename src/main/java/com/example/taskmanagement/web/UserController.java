package com.example.taskmanagement.web;

import com.example.taskmanagement.models.User;
import com.example.taskmanagement.models.UserRole;
import com.example.taskmanagement.models.dto.UserDTO;
import com.example.taskmanagement.models.dto.UserLoginDTO;
import com.example.taskmanagement.service.UserService;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;


    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public List<User> getAll(){
        return userService.getAllUsers();
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDTO newUser) {
        boolean userExists = userService.isUsernameAvailable(newUser.getUsername());

        if (userExists) {
            return ResponseEntity.badRequest().body("Username already exists");
        }

        try {
            String encodedPassword = passwordEncoder.encode(newUser.getPassword());
            User user = new User(newUser.getUsername(), newUser.getEmail(), encodedPassword, UserRole.USER_ROLE);
            userService.register(user);
        }
        catch (Exception ex){
            return ResponseEntity.internalServerError().body("Error 500 something went wrong");
        }

        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login (@RequestBody UserLoginDTO user){
        boolean userExsists = userService.authenticateUser(user.getUsernameOrEmail(), user.getPassword());

        if (userExsists){
            return ResponseEntity.ok().build();
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/forgotPassword")
    public ResponseEntity<?> forgotPassword (@RequestBody UserLoginDTO user) {
        boolean userExsists = userService.authenticateUser(user.getUsernameOrEmail(), user.getPassword());


        if (userExsists){
            //send email to user.email
            return ResponseEntity.ok().build();
        }
        else{
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping("/saveForgotenPassword/{id}")
    public ResponseEntity<?> saveForgotenPassword(@PathVariable Long userId, @RequestBody String newPassword) {

        String encodedPassword = passwordEncoder.encode(newPassword);

        try {
            userService.forgotPassword(userId, encodedPassword);
            return ResponseEntity.ok().build();
        }
        catch (Exception ex){
            return ResponseEntity.internalServerError().build();
        }

    }
}
