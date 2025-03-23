package com.strechdstudio.app.config;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        // Return a 401 Unauthorized response when the user tries to access a protected resource without authentication
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized: Token is missing or invalid");
    }
}
