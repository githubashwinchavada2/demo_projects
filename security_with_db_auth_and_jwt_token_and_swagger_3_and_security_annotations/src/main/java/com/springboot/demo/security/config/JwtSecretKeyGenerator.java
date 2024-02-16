package com.springboot.demo.security.config;

import java.util.Base64;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class JwtSecretKeyGenerator {

    public static String generateSecretKey() {
//        Generate a random 256-bit (32-byte) secret key
        SecretKey secretKey = createSecretKey();

//        Convert the secret key to a Base64-encoded string
        String base64EncodedKey = encodeSecretKey(secretKey);

        return base64EncodedKey;
    }
    public static SecretKey createSecretKey() {
//        Use a secure random generator to create a byte array
        byte[] bytes = new byte[64];

//        Populate the byte array with random values
        new java.security.SecureRandom().nextBytes(bytes);

//        Create a SecretKey using the byte array
        return new SecretKeySpec(bytes, "HmacSHA512");
    }

    public static String encodeSecretKey(SecretKey secretKey) {
//        Encode the secret key to Base64
        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }
}
