package com.springboot.demo.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import lombok.AllArgsConstructor;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    /** Creating InMemory Authentication */

    @Bean
//    public InMemoryUserDetailsManager userDetailsService() {
    public UserDetailsService userDetailsService() {
        UserDetails adminUser = User.builder()
                .username("a@a.com")
                .password(bCryptPasswordEncoder().encode("admin"))
                .roles("ADMIN")
                .build();

        UserDetails normalUser = User.builder()
                .username("u@u.com")
                .password(bCryptPasswordEncoder().encode("user"))
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(adminUser, normalUser);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//       Enable CORS and disable CSRF
        http.httpBasic().and().cors().and().csrf().disable()
        .authorizeRequests()
        .antMatchers("/home/**").hasRole("ADMIN")
        .antMatchers("/user/**").hasRole("USER")
//        .antMatchers("/**").permitAll() // permit all URLs
//        .anyRequest().permitAll() // permit all URLs
        .anyRequest().authenticated()
        .and().exceptionHandling(ex -> ex.authenticationEntryPoint(customAuthenticationEntryPoint))
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .formLogin().permitAll()
        .and().logout().permitAll();
    }
}
