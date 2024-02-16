package com.springboot.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.springboot.demo.entity.User;
import com.springboot.demo.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    @Lazy
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public User addUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRole("ROLE_" + user.getRole().toUpperCase());
        user = userRepository.save(user);
        return user;
    }

    public User getUser(String emailId) {
        User user = userRepository.findByEmailId(emailId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Could not find user with email id: " + emailId));
        return user;
    }

    public List<User> getUser() {
        List<User> users = userRepository.findAll();

        if (users.isEmpty()) {
            new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find users");
        }
        return users;
    }
}
