package com.springboot.demo.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.springboot.demo.security.config.BasicAuthCredentialsDto;

@ConfigurationProperties("basic-auth")
public class CustomConfigurationProperties {

    private final List<BasicAuthCredentialsDto> basicAuthCredentialsDto = new ArrayList<>();

    public List<BasicAuthCredentialsDto> getCredentials() {
        return basicAuthCredentialsDto;
    }
}
