package com.springboot.demo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.demo.entity.User;
import com.springboot.demo.service.UserService;
import com.springboot.dependent.service.DependentService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    public final UserService userService;
    public final DependentService dependentService;

//    http://localhost:8080/swagger-ui/index.html
//    http://localhost:8080/v2/api-docs
//    http://localhost:8080/v3/api-docs
//    http://localhost:8080/user

    @PostMapping(value = "/add-user", consumes = "application/json")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        User addedUser = userService.addUser(user);
        return ResponseEntity.ok(addedUser);
    }

    @GetMapping(value = "/get-user", produces = "application/json")
    public ResponseEntity<User> getUser(@RequestParam("emailId") String emailId) {
        User fetchedUser = userService.getUser(emailId);
        return ResponseEntity.ok(fetchedUser);
    }

    @GetMapping(value = "/get-all-users", produces = "application/json")
    public ResponseEntity<List<User>> getUser() {
        List<User> fetchedUsers = userService.getUser();
        return ResponseEntity.ok(fetchedUsers);
    }

    @GetMapping(value = "/get-dependent-data", produces = "application/json")
    public ResponseEntity<String> getDependentData() {
        return ResponseEntity.ok(dependentService.getDependentData());
//        return ResponseEntity.ok("");
    }
}
