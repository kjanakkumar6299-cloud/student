package com.student.student.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.student.student.entity.User;
import com.student.student.repository.UserRepository;

@Controller
public class LoginController {

    @Autowired
    private UserRepository repo;

    // 🔥 Home page (auth)
    @GetMapping("/")
    public String home() {
        return "auth";
    }

    // 🔐 Login check
    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password) {

        User user = repo.findByEmailAndPassword(email, password);

        if (user != null) {
            return "dashboard";
        } else {
            return "auth";
        }
    }

    // 🔄 Reset password (same page)
    @PostMapping("/forgot")
    public String resetPassword(@RequestParam String email,
                                @RequestParam String newPassword) {

        User user = repo.findByEmail(email);

        if (user != null) {
            user.setPassword(newPassword);
            repo.save(user);
        }

        return "auth";
    }
}