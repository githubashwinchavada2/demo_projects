package com.springboot.demo.security.config;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtHelper {

//    milliseconds (1 * 60 * 60 * 1000 = 1 hour) (30 * 60 * 1000 = 30 minutes) (60 * 1000 = 1 minute)  (30 * 1000 = 30 seconds)
    public static final long JWT_TOKEN_VALIDITY = 30 * 60 * 1000;

//    private static final String SECRET_KEY = JwtSecretKeyGenerator.generateSecretKey();
    private static final String SECRET_KEY = JwtSecretKeyGenerator.encodeSecretKey(Keys.secretKeyFor(SignatureAlgorithm.HS512));
//    private static final String SECRET_KEY = "QI/hOLtR/lpR60zreCrLvws41y7l9H/pG+Hxht4y1iT7wyuIu2pAJXVlRVpvsVLxF7s6lSZySZZTXAUUI0RBZQ==";

    private Key getSignKey() { 
        byte[] keyBytes= Decoders.BASE64.decode(SECRET_KEY); 
        return Keys.hmacShaKeyFor(keyBytes); 
    } 

//  generate token for user
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());
    }

//    while creating the token -
//    1. Define  claims of the token, like Issuer, Expiration, Subject, and the ID
//    2. Sign the JWT using the HS512 algorithm and secret key.
//    3. According to JWS Compact Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1) compaction of the JWT to a URL-safe string
    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
                .signWith(getSignKey(), SignatureAlgorithm.HS512)
                .compact();
    }

//    retrieve username from jwt token
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

//    retrieve expiration date from jwt token
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

//    for retrieveing any information from token we will need the secret key
    private Claims getAllClaimsFromToken(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

//    check if the token has expired
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

//    validate token
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}