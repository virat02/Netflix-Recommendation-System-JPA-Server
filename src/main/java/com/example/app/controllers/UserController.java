package com.example.app.controllers;

import com.example.app.models.User;
import com.example.app.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
@CrossOrigin(origins = "*", allowCredentials = "true")
public class UserController {

    private UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/api/user")
    public Iterable<User> findAllUsers(@RequestParam(name = "username", required = false) String username) {
        if (username != null)
            return userRepository.findUserByUsername(username);
        return userRepository.findAll();
    }

    @PostMapping("api/login")
    public User login(@RequestBody User user, HttpServletRequest request, HttpServletResponse response) {
        User existingUser = userRepository.findUserByCredentials(user.getUsername(), user.getPassword()).orElse(null);
        if (existingUser != null) {
            HttpSession session = request.getSession(true);
            session.setAttribute("currentUser", existingUser);
            response.setStatus(200);
            return existingUser;
        }

        response.setStatus(204);
        return null;
    }

    @GetMapping("api/logout")
    public void logout(HttpSession session) {
        session.invalidate();
    }

    @DeleteMapping("api/user/{userId}")
    public void deleteUser(@PathVariable("userId") Long userId) {
        userRepository.delete(userRepository.findById(userId).get());
    }
}
