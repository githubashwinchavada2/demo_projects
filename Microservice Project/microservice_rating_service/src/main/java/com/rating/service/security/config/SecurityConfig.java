package com.rating.service.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true, // enables @Secured annotation
        jsr250Enabled = true, // enables @RolesAllowed annotation
        prePostEnabled = true // enables @PreAuthorize, @PostAuthorize, @PreFilter, @PostFilter annotations
    )
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests()
            .anyRequest().authenticated()
            .and().oauth2ResourceServer()
            .jwt();

        return http.build();
    }
}
