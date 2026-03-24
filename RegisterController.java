package com.student.student.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.student.student.entity.User;
import com.student.student.repository.UserRepository;

@Controller
public class RegisterController {

    @Autowired
    private UserRepository repo;



    // 🔥 Signup page open karne ke liye
    @GetMapping("/signup")
    public String signupPage() {
        return "signup";
    }

    // 🔥 Form submit hone par data save
    @PostMapping("/register")
    public String register(@RequestParam String name,
                           @RequestParam String email,
                           @RequestParam String password) {

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);

        repo.save(user);

        return "login";
    }
}