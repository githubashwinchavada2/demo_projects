package com.springboot.demo.controller;

import java.security.Principal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.demo.service.UserService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/home")
@AllArgsConstructor
public class HomeController {

    public final UserService userService;

//    http://localhost:8080/home

    @GetMapping(value = "/")
    public ResponseEntity<String> home() {
        return ResponseEntity.ok("Hey There!");
    }

    @GetMapping(value = "/logged-in-user")
    public ResponseEntity<String> getLoggedInUser(Principal principal) {
        return ResponseEntity.ok(principal.getName());
    }
}
