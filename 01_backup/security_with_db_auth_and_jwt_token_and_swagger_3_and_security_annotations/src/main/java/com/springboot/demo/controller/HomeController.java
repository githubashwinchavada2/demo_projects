package com.springboot.demo.controller;

import java.security.Principal;

import javax.annotation.security.RolesAllowed;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.demo.service.UserService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/home")
@AllArgsConstructor
//@SecurityRequirement(name = "bearerAuth") // to apply bearer auth at class level
//@SecurityRequirements // to remove bearer auth at class level
public class HomeController {

    public final UserService userService;

//  http://localhost:8080/swagger-ui/index.html
//  http://localhost:8080/v3/api-docs
//  http://localhost:8080/home

//    @SecurityRequirement(name = "bearerAuth") // to apply bearer auth at class level
//    @SecurityRequirements // to remove bearer auth at class level
    @RolesAllowed("ROLE_ADMIN") // or use @PreAuthorize("hasRole('ROLE_ADMIN')") or @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping(value = "/")
    public ResponseEntity<String> home() {
        return ResponseEntity.ok("Hey There!");
    }

    @RolesAllowed("ROLE_ADMIN")
    @GetMapping(value = "/logged-in-user")
    public ResponseEntity<String> getLoggedInUser(Principal principal) {
        return ResponseEntity.ok(principal.getName());
    }
}
