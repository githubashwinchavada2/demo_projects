package com.springboot.demo.security.config;

import javax.annotation.security.RolesAllowed;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.AllArgsConstructor;

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

    public static final String[] PUBLIC_URLS = {
            "/auth/generate-token", // for JWT token
            "/auth/refresh-token", // for JWT refresh token
            "/v3/api-docs", // for swagger
            "/v2/api-docs", // for swagger
            "/swagger-resources/**", // for swagger
            "/swagger-ui/**", // for swagger
            "/swagger-ui.html", // for swagger
            "/v3/api-docs/**", // for swagger 3 documentation
            "/webjars/**" // WebJars are client side dependencies packaged into JAR archive files. such as Twitter, Bootstrap, jQuery, Angular JS, Chart.js etc dependency
    };

    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new  DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(bCryptPasswordEncoder());
        return authenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.httpBasic().and().cors().and().csrf().disable()
            .authorizeHttpRequests()
            .antMatchers(PUBLIC_URLS).permitAll()
//          .antMatchers("/home/**").authenticated()
//            .antMatchers("/home/**").hasRole("ADMIN")
//            .antMatchers("/user/**").hasRole("USER") // use URL-based configuration for role authorization
//            or use Annotation-based configuration role authorization - use @RolesAllowed("ROLE_ADMIN") / @PreAuthorize("hasRole('ROLE_ADMIN')") annotation at method level
            .anyRequest().authenticated()
            .and().exceptionHandling(ex -> ex.authenticationEntryPoint(customAuthenticationEntryPoint))
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authenticationProvider(authenticationProvider())
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfiguration) throws Exception {
        return authConfiguration.getAuthenticationManager();
    }
}