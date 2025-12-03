package com.bhavanisankar.filestorage.controller;

import com.bhavanisankar.filestorage.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    @Autowired
    private AuthService authService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // @GetMapping("/register")
    // public String register() {
    //     return "register";
    // }

    @PostMapping("/register")
    public String registerUser(@RequestParam String username, @RequestParam String password) {
        authService.registerUser(username, password);
        return "redirect:/login?registered";
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }
}