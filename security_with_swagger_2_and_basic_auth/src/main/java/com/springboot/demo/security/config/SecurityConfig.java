package com.springboot.demo.security.config;

import java.util.ArrayList;
import java.util.List;

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

import com.springboot.demo.config.CustomConfigurationProperties;

import lombok.AllArgsConstructor;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    public CustomConfigurationProperties basicAuthCredentials;
    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    public static final String[] PUBLIC_URLS = {
            "/v3/api-docs", // for swagger
            "/v2/api-docs", // for swagger
            "/swagger-resources/**", // for swagger
            "/swagger-ui/**", // for swagger
            "/webjars/**" // WebJars are client side dependencies packaged into JAR archive files. such as Twitter, Bootstrap, jQuery, Angular JS, Chart.js etc dependency
    };

    @Bean
    public UserDetailsService userDetailsService() {
        List<UserDetails> users = new ArrayList<>();

        for (BasicAuthCredentialsDto cred : basicAuthCredentials.getCredentials()) {
            UserDetails user = User.builder()
                    .username(cred.getUsername())
                    .password(bCryptPasswordEncoder().encode(cred.getPassword()))
                    .roles(cred.getRoles())
                    .build();
            users.add(user);
        }

        return new InMemoryUserDetailsManager(users);
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
        .antMatchers(PUBLIC_URLS).permitAll()
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
