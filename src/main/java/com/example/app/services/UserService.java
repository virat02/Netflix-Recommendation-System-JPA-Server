package com.example.app.services;

import com.example.app.Utils;
import com.example.app.models.User;
import com.example.app.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class UserService extends Utils {

    @Autowired
    private UserRepository userRepo;

    private static final String CURRENT_USER = "currentUser";

    /**
     *
     * @param user
     * @param session
     * @return
     */
    @CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
    @PostMapping("/api/login")
    public User login(@RequestBody User user, HttpSession session) {
        List<User> users = (List<User>) userRepo.findAll();

        for(User u: users) {
            if(u.getUsername().equals(user.getUsername()) && u.getPassword().equals(user.getPassword())) {
                session.setAttribute(CURRENT_USER, u);
                return u;
            }
        }

        return new User();
    }

    /**
     *
     * @param user
     * @param session
     * @return
     */
    @CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
    @PostMapping("/api/register")
    public User register(@RequestBody User user, HttpSession session) {
        session.setAttribute(CURRENT_USER, user);
        userRepo.save(user);
        return user;
    }

    /**
     *
     * @param session
     * @return
     */
    @CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
    @PostMapping("/api/loggedin")
    public User getLoggedInUser(HttpSession session) {
        return (User) session.getAttribute(CURRENT_USER);
    }

    @GetMapping("/api/user")
    public Iterable<User> findAllUsers(@RequestParam(name = "username", required = false) String username) {
        if (username != null)
            return userRepo.findUserByUsername(username);
        return userRepo.findAll();
    }

}
