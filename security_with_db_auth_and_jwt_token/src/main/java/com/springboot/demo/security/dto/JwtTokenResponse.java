package com.springboot.demo.security.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtTokenResponse {

    private String jwtToken;

    private String refreshToken;

    private String username;
}
