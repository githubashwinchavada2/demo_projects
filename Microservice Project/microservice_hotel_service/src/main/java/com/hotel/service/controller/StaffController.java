package com.hotel.service.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/staff")
public class StaffController {

    @GetMapping(value = "/get-all-staff", produces = "application/json")
    public ResponseEntity<List<String>> getHotels() {
        List<String> staffMembers = Arrays.asList("Ram", "Shyam", "Ghanshyam");
        return ResponseEntity.ok(staffMembers);
    }
}
