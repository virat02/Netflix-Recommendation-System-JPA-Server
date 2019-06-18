package com.example.app.controllers;

import com.example.app.models.User;
import com.example.app.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/api/user")
    public Iterable<User> findAllUsers(@RequestParam(name = "username", required = false) String username) {
        if (username != null)
            return userRepository.findUserByUsername(username);
        return userRepository.findAll();
    }
}
