package com.example.fileusers.controller;

import com.example.fileusers.model.User;
import com.example.fileusers.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public String addUser(@RequestBody User user) throws IOException {
        userService.addUser(user);
        return "User added!";
    }

    @GetMapping
    public List<User> getAllUsers() throws IOException {
        return userService.getAllUsers();
    }

    @PutMapping("/{id}")
    public String updateUser(@PathVariable int id, @RequestBody User user) throws IOException {
        user.setId(id);
        return userService.updateUser(user) ? "User updated!" : "User not found!";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable int id) throws IOException {
        return userService.deleteUser(id) ? "User deleted!" : "User not found!";
    }
}
