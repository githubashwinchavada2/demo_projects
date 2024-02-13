package com.springboot.demo.controller;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.demo.security.config.BasicAuth;
import com.springboot.demo.service.UserService;

@RestController
@RequestMapping("/home")
public class HomeController {

    @Autowired
    public UserService userService;

//    http://localhost:8080/home

    @Value("${basicAuth.enable}")
    private Boolean isBasicAuthEnabled;

    @Value("${basicAuth.realm}")
    private String realm;

    @Value("${basicAuth.credentials}")
    private List<String> basicAuthCredentials;

    @GetMapping(value = "/basic-auth")
    public @ResponseBody ResponseEntity<?> basicAuth(HttpServletRequest request) {
        ResponseEntity<Object> basicAuthResponse = BasicAuth.doBasicAuth(request, isBasicAuthEnabled, realm, basicAuthCredentials);
        if (basicAuthResponse != null) {
            return basicAuthResponse;
        }
        return ResponseEntity.status(HttpStatus.OK).body("Test Api Success.");
    }

    @GetMapping(value = "/")
    public ResponseEntity<String> home() {
        return ResponseEntity.ok("Hey There!");
    }

    @GetMapping(value = "/logged-in-user")
    public ResponseEntity<String> getLoggedInUser(Principal principal) {
        return ResponseEntity.ok(principal.getName());
    }
}
