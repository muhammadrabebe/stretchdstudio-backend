package com.strechdstudio.app.controller;

import com.strechdstudio.app.dto.ApiResponse;
import com.strechdstudio.app.dto.LoginRequest;
import com.strechdstudio.app.dto.RegisterRequest;
import com.strechdstudio.app.model.User;
import com.strechdstudio.app.service.AuthService;
import com.strechdstudio.app.util.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    // Login Endpoint
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> login(@RequestBody LoginRequest loginRequest) {
        try {
            String token = authService.authenticateAndGenerateToken(loginRequest);
            ApiResponse<String> response = new ApiResponse<>("Login successful", 200, token);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse<String> response = new ApiResponse<>(e.getMessage(), 401, null);
            return ResponseEntity.status(401).body(response);
        }
    }

    // Logout Endpoint
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<String>> logout(@RequestParam UUID userId) {
        try {
            authService.logout(userId);
            ApiResponse<String> response = new ApiResponse<>("Logout successful", 200, null);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse<String> response = new ApiResponse<>(e.getMessage(), 400, null);
            return ResponseEntity.status(400).body(response);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@RequestBody RegisterRequest registerRequest) {
        // Register the user (this part depends on your registration logic)
        User user = authService.registerUser(registerRequest);

        // After registration, authenticate the user
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(user.getUsername(), registerRequest.getPassword())
//        );

        // Generate JWT token
        String jwtToken = jwtUtil.generateToken(user);

        // Send the response with the token
        ApiResponse<String> response = new ApiResponse<>("Registration successful", 200, jwtToken);
        return ResponseEntity.ok(response);
    }
}
