package com.springboot.demo.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import lombok.AllArgsConstructor;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    public static final String[] PUBLIC_URLS = {
            "/v3/api-docs", // for swagger
            "/v2/api-docs", // for swagger
            "/swagger-resources/**", // for swagger
            "/swagger-ui/**", // for swagger
            "/swagger-ui.html", // for swagger
            "/v3/api-docs/**", // for swagger 3 documentation
            "/webjars/**" // WebJars are client side dependencies packaged into JAR archive files. such as Twitter, Bootstrap, jQuery, Angular JS, Chart.js etc dependency
    };

    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    /** Creating Database based Authentication */
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

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
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
