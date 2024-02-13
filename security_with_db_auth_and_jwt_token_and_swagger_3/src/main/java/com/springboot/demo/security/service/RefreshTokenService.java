package com.springboot.demo.security.service;

import java.time.Instant;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.springboot.demo.entity.User;
import com.springboot.demo.security.entity.RefreshToken;
import com.springboot.demo.security.repository.RefreshTokenRepository;
import com.springboot.demo.service.UserService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RefreshTokenService {

//    milliseconds (1 * 60 * 60 * 1000 = 1 hour) (30 * 60 * 1000 = 30 minutes) (60 * 1000 = 1 minute) (30 * 1000 = 30 seconds)
    public static final long REFRESH_TOKEN_VALIDITY = 1 * 60 * 60 * 1000;

    private final RefreshTokenRepository refreshTokenRepository;

    private final UserService userService;

    public RefreshToken generateRefreshToken(String username) {
        User user = userService.getUser(username);
        RefreshToken refreshToken = user.getRefreshToken();

        if (refreshToken == null) {
            refreshToken = RefreshToken.builder()
                    .refreshToken(UUID.randomUUID().toString())
                    .expiry(Instant.now().plusMillis(REFRESH_TOKEN_VALIDITY))
                    .user(user)
                    .build();
        } else {
            refreshToken.setExpiry(Instant.now().plusMillis(REFRESH_TOKEN_VALIDITY));
        }

        user.setRefreshToken(refreshToken);

        refreshTokenRepository.save(refreshToken);

         return refreshToken;
    }

    @Transactional(noRollbackFor = ResponseStatusException.class)
    public RefreshToken isRefreshTokenExpired(String refreshToken) {
        RefreshToken refToken = refreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() ->  new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find refresh token"));

        if (refToken.getExpiry().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.deleteById(refToken.getId());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Refresh Token has expired!");
        }

        return refToken;
    }
}
