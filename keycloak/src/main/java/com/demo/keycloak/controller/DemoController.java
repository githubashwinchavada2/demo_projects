package com.demo.keycloak.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/keycloak")
public class DemoController {

    @PreAuthorize("hasRole('USER_ACCESS')")
    @GetMapping("/user")
    public String user() {
        return "Hello user!";
    }

    @PreAuthorize("hasRole('ADMIN_ACCESS')")
    @GetMapping("/admin")
    public String admin() {
        return "Hello admin!";
    }
}
