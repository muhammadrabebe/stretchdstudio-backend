package com.strechdstudio.app.util;

import com.strechdstudio.app.model.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Base64;

@Component
public class JwtUtil {

    @Value("${jwt.secret}") // Load the key from application.properties
    private String secretKey;

//    private SecretKey getSigningKey() {
//        byte[] keyBytes = Base64.getDecoder().decode(secretKey); // Properly decode the key
//        return Keys.hmacShaKeyFor(keyBytes);
//    }

    private SecretKey getSigningKey() {
        byte[] keyBytes;
        try {
            keyBytes = Base64.getDecoder().decode(secretKey);
            System.out.println("Decoded Key Length (bytes): " + keyBytes.length);
            System.out.println("Decoded Key Length (bits): " + (keyBytes.length * 8));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid Base64-encoded secret key. Ensure it is properly generated.", e);
        }

        if (keyBytes.length < 64) {
            throw new RuntimeException("Secret key must be at least 64 bytes (512 bits).");
        }

        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(User userDetails) {
        // 1 day (milliseconds)
        long EXPIRATION_TIME = 86400000;
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSigningKey(), SignatureAlgorithm.HS512) // Use a secure key
                .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey()).build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token, String username) {
        return (username.equals(extractUsername(token)) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey()).build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration()
                .before(new Date());
    }
}
