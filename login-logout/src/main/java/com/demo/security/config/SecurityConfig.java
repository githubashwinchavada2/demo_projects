package com.demo.security.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true, // enables @Secured annotation
        jsr250Enabled = true, // enables @RolesAllowed annotation
        prePostEnabled = true // enables @PreAuthorize, @PostAuthorize, @PreFilter, @PostFilter annotations
)
@AllArgsConstructor
public class SecurityConfig {

    protected static final String[] PUBLIC_URLS = {""};

    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails adminUser = User.builder()
                .username("admin")
                .password(bCryptPasswordEncoder().encode("admin"))
                .roles("ADMIN")
                .build();

        UserDetails normalUser = User.builder()
                .username("user")
                .password(bCryptPasswordEncoder().encode("user"))
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(adminUser, normalUser);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.httpBasic(Customizer.withDefaults())
//                .csrf(csrf -> csrf.disable())  // Disable CSRF protection when application's authentication is stateless (e.g., not using httpsession)
//                .cors(cors -> cors.disable())  // Disable CORS configuration when application's authentication is stateless  (e.g., not using httpsession)

                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
//                        .requestMatchers(PUBLIC_URLS).permitAll()
                        .anyRequest().authenticated())

                .exceptionHandling(ex -> ex.authenticationEntryPoint(customAuthenticationEntryPoint))

//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .sessionManagement(session -> session
                        .maximumSessions(1) // Allow only one session per user
                        .expiredUrl("/api/logout") // Redirect to logout URL when session expires
                        .maxSessionsPreventsLogin(true)); // New login will invalidate the old session

        return http.build();
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfig = new CorsConfiguration();

        corsConfig.setAllowCredentials(true); // Allow credentials (e.g., cookies)
        corsConfig.setAllowedOrigins(Collections.singletonList("*")); // Allow requests from specific origins
        corsConfig.setAllowedHeaders(Arrays.asList("authorization", "content-type")); // Allow specific headers
        corsConfig.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")); // Allow specific HTTP methods

        source.registerCorsConfiguration("/**", corsConfig);
        return new CorsFilter(source);
    }
}
