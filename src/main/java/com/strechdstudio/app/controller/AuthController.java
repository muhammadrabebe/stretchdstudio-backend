package com.strechdstudio.app.controller;

import com.strechdstudio.app.dto.*;
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

    @PostMapping("/validate")
    public ResponseEntity<ApiResponse<ValidateResponse>> validateUser(@RequestBody ValidateRequest validateRequest) {
        ValidateResponse response = authService.checkUserExists(validateRequest);

        if (response.getUserExists()) {
            return ResponseEntity.ok(new ApiResponse<>("User exists", 200, response));
        } else {
            return ResponseEntity.status(404).body(new ApiResponse<>("User not found", 404, response));
        }
    }



    // Login Endpoint
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@RequestBody LoginRequest loginRequest) {
        try {
            LoginResponse loginResponse = authService.authenticateAndGenerateToken(loginRequest);
            ApiResponse<LoginResponse> response = new ApiResponse<>("Login successful", 200, loginResponse);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new ApiResponse<>(e.getMessage(), 400, null));
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
    public ResponseEntity<ApiResponse<RegisterResponse>> register(@RequestBody RegisterRequest registerRequest) {
        // Register the user (this part depends on your registration logic)
        RegisterResponse registerResponse = authService.registerUser(registerRequest);

        // Send the response with the token
        ApiResponse<RegisterResponse> response = new ApiResponse<>("Registration successful", 200, registerResponse);
        return ResponseEntity.ok(response);
    }
}
