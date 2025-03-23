package com.strechdstudio.app.service;

import com.strechdstudio.app.dto.LoginRequest;
import com.strechdstudio.app.dto.RegisterRequest;
import com.strechdstudio.app.model.User;
import com.strechdstudio.app.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;
import java.util.Base64;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${jwt.secret}") // Inject the secret key from the application.properties
    private String jwtSecret;

    // Convert the jwtSecret String into a SecretKey
    private Key getSigningKey() {
        // Ensure jwtSecret is Base64 encoded and at least 512 bits (64 bytes) long
        return Keys.hmacShaKeyFor(Base64.getDecoder().decode(jwtSecret));
    }

    // Authenticate and generate JWT Token
    public String authenticateAndGenerateToken(LoginRequest loginRequest) throws Exception {
        // Try to find the user by username or email
        User user = userRepository.findByUsername(loginRequest.getUsername())
                .orElseGet(() -> {
                    try {
                        return userRepository.findByEmail(loginRequest.getEmail())
                                .orElseThrow(() -> new Exception("User not found"));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });

        // Check if the password matches
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new Exception("Invalid credentials");
        }

        // Update last login timestamp
        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);

        // Generate JWT token
        return generateJwtToken(user);
    }


    // Handle logout
    public void logout(String username) throws Exception {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new Exception("User not found"));

        // Update last logout timestamp
        user.setLastLogout(LocalDateTime.now());
        userRepository.save(user);
    }

    // Generate JWT token for authenticated user
    private String generateJwtToken(User user) {
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 24 hours
                .signWith(getSigningKey(), SignatureAlgorithm.HS512) // Use the converted SecretKey
                .compact();
    }

    @Transactional
    public User registerUser(RegisterRequest registerRequest) {
        // Check if the user already exists
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new RuntimeException("Email is already taken.");
        }

        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            throw new RuntimeException("Username is already taken.");
        }
        LocalDateTime currentDate = LocalDateTime.now();
        // Create the user object
        User newUser = new User();
        newUser.setEmail(registerRequest.getEmail());
        newUser.setUsername(registerRequest.getUsername());
        newUser.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        newUser.setPhoneNumber(registerRequest.getPhoneNumber());
        newUser.setActive(true); // Default to active on registration
        newUser.setLastLogin(null);
        newUser.setLastLogout(null);
        newUser.setAddDate(currentDate);
        newUser.setEditDate(currentDate);

        // Save the user in the database
        return userRepository.save(newUser);
    }
}
