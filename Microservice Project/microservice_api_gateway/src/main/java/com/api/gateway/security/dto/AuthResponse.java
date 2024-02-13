package com.api.gateway.security.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthResponse {

    private String userId;

    private String accessToken;

    private String refreshToken;

    private Long expiresAt;

    private List<String> authorities;
}
