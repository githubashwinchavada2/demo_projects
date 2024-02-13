package com.springboot.dependent.service;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DependentService {

    public String getDependentData() {
        return "this is dependent data";
    }
}
