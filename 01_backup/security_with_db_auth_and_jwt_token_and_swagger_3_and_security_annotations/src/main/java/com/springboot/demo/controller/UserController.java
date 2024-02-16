package com.springboot.demo.controller;

import java.util.List;

import javax.annotation.security.RolesAllowed;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.demo.entity.User;
import com.springboot.demo.service.UserService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    public final UserService userService;

//    http://localhost:8080/user

    @RolesAllowed("ROLE_USER")
    @PostMapping(value = "/add-user", consumes = "application/json")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        User addedUser = userService.addUser(user);
        return ResponseEntity.ok(addedUser);
    }

    @RolesAllowed("ROLE_USER")
    @GetMapping(value = "/get-user", produces = "application/json")
    public ResponseEntity<User> getUser(@RequestParam("emailId") String emailId) {
        User fetchedUser = userService.getUser(emailId);
        return ResponseEntity.ok(fetchedUser);
    }

    @RolesAllowed("ROLE_USER") // or use @PreAuthorize("hasRole('ROLE_USER')") or @PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping(value = "/get-all-users", produces = "application/json")
    public ResponseEntity<List<User>> getUser() {
        List<User> fetchedUsers = userService.getUser();
        return ResponseEntity.ok(fetchedUsers);
    }
}