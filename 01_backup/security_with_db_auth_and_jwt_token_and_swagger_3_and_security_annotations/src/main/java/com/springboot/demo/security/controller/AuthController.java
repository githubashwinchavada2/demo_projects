package com.springboot.demo.security.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.springboot.demo.entity.User;
import com.springboot.demo.security.config.CustomUserDetails;
import com.springboot.demo.security.config.CustomUserDetailsService;
import com.springboot.demo.security.config.JwtHelper;
import com.springboot.demo.security.dto.JwtTokenRequest;
import com.springboot.demo.security.dto.JwtTokenResponse;
import com.springboot.demo.security.dto.RefreshTokenRequest;
import com.springboot.demo.security.entity.RefreshToken;
import com.springboot.demo.security.service.RefreshTokenService;

import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
@Slf4j
@SecurityRequirements
public class AuthController {

    private final CustomUserDetailsService customUserDetailsService;
    private final AuthenticationManager authenticationManager;
    private final JwtHelper jwtHelper;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/generate-token")
    public ResponseEntity<JwtTokenResponse> generateJwtToken(@RequestBody JwtTokenRequest jwtTokenRequest) {
        log.info("Jwt Token Request[{}]", jwtTokenRequest);

        authenticate(jwtTokenRequest.getEmailId(), jwtTokenRequest.getPassword());

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(jwtTokenRequest.getEmailId());

        String jwtToken = jwtHelper.generateToken(userDetails);
        RefreshToken refreshToken = refreshTokenService.generateRefreshToken(userDetails.getUsername());

        JwtTokenResponse jwtTokenResponse = JwtTokenResponse.builder()
                .jwtToken(jwtToken)
                .refreshToken(refreshToken.getRefreshToken())
                .username(userDetails.getUsername())
                .build();

        log.info("Jwt Token Response[{}]", jwtTokenResponse);

        return new ResponseEntity<>(jwtTokenResponse, HttpStatus.OK);
    }

    private void authenticate(String emailId, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(emailId, password);
        try {
            authenticationManager.authenticate(authenticationToken);
        } catch (BadCredentialsException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid username or password!");
        }
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<JwtTokenResponse> refreshJwtToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        log.info("Jwt Refresh Token Request[{}]", refreshTokenRequest);

        RefreshToken refreshToken = refreshTokenService.isRefreshTokenExpired(refreshTokenRequest.getRefreshToken());
        User user = refreshToken.getUser();
        String jwtToken = jwtHelper.generateToken(new CustomUserDetails(user));

        JwtTokenResponse jwtTokenResponse = JwtTokenResponse.builder()
                .jwtToken(jwtToken)
                .refreshToken(refreshToken.getRefreshToken())
                .username(user.getEmailId())
                .build();

        log.info("Jwt Refresh Token Response[{}]", jwtTokenResponse);

        return new ResponseEntity<>(jwtTokenResponse, HttpStatus.OK);
    }

    @ExceptionHandler(Exception.class)
    public String exceptionHanlder(Exception ex) {
        return ex.getMessage();
    }
}
