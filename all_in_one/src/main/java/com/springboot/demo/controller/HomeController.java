package com.springboot.demo.controller;

import java.security.Principal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.demo.service.UserService;

import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/home")
@AllArgsConstructor
@Slf4j
//@SecurityRequirement(name = "bearerAuth") // to apply bearer auth at class level
//@SecurityRequirements // to remove bearer auth at class level
public class HomeController {

//    https://springdoc.org/properties.html
//    http://localhost:8080/swagger-ui/index.html
//    http://localhost:8080/v3/api-docs
//    http://localhost:8080/home

    public final UserService userService;

//  @SecurityRequirement(name = "bearerAuth")  // to apply bearer auth at class level
//  @SecurityRequirements // to remove bearer auth at class level
    @GetMapping(value = "/")
    public ResponseEntity<String> home() {
        log.info("Hey There!");
        return ResponseEntity.ok("Hey There!");
    }

    @GetMapping(value = "/logged-in-user")
    public ResponseEntity<String> getLoggedInUser(Principal principal) {
        return ResponseEntity.ok(principal.getName());
    }
}
