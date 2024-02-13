package com.springboot.demo.security.config;

import lombok.Data;

@Data
public class BasicAuthCredentialsDto {

    private String username;
    private String password;
    private String[] roles;
}
