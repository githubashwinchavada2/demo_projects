package com.springboot.demo.security.config;


import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Builder
@Data
@Slf4j
public class BasicAuth {

    private static final String WWW_AUTHENTICATE = "www-authenticate";
    private static final String BASIC_REALM = "Basic realm=\"";
    private static String uName;
    private String username;
    private String password;

    public static ResponseEntity<Object> doBasicAuth(HttpServletRequest request,
            Boolean isBasicAuthEnabled, String realm, List<String> basicAuthCredentials) {
        if (!isBasicAuthEnabled) {
            return null;
        }
        BasicAuth basicAuth = BasicAuth.getDetails(request);
        if (basicAuth == null) {
            return BasicAuth.malformed(realm);
        }
        if (!(basicAuthCredentials.contains(basicAuth.getUsername() + ":" + basicAuth.getPassword()))) {
            return BasicAuth.invalidCredentials(realm);
        }
        setUName(basicAuth.getUsername());
        return null;
    }

    public static BasicAuth getDetails(HttpServletRequest request) {
        final String authorization = request.getHeader("Authorization");
        if (authorization == null) {
            return null;
        }
        try {
            if (authorization.toLowerCase().startsWith("basic")) {
                String base64Credentials = authorization.substring("Basic".length()).trim();
                byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
                String credentials = new String(credDecoded, StandardCharsets.UTF_8);
                final String[] values = credentials.split(":", 2);
                return BasicAuth.builder().username(values[0]).password(values[1]).build();
            }
        } catch (Exception e) {
            log.error(request.getRequestURL().toString());
            log.error(request.getRemoteAddr());
            log.error("Basic Auth", e);
        }
        return null;
    }

    public static ResponseEntity<Object> malformed(String realm) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .header(WWW_AUTHENTICATE, BASIC_REALM + realm + "\"")
                .body("Basic authorization has missed or malformed");
    }

    public static ResponseEntity<Object> invalidCredentials(String realm) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .header(WWW_AUTHENTICATE, BASIC_REALM + realm + "\"")
                .body("Invalid credentials");
    }

    public static ResponseEntity<Object> userNotFound(String realm) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .header(WWW_AUTHENTICATE, BASIC_REALM + realm + "\"")
                .body("User not found");
    }

    public static String getUName() {
        return uName;
    }

    public static void setUName(String userName) {
        uName = userName;
    }
}
