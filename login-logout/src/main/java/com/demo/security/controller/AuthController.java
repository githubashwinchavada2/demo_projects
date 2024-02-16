package com.demo.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @GetMapping("/test")
    public String test() {
        return "test Successful!";
    }

    @GetMapping("/login")
    public String login() {
        return "Login Successful!";
    }

    @PostMapping("/logout")
    public String logout() {
        return "Logout Successful!";
    }
}
