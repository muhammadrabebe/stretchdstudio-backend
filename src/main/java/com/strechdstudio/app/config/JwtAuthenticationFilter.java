package com.strechdstudio.app.config;

import com.strechdstudio.app.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil; // Utility to validate and extract JWT

    @Autowired
    private UserDetailsService userDetailsService; // UserDetailsService to load user by username

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");

        String jwt = null;
        String username = null;

        // Check if the Authorization header exists and starts with "Bearer "
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7); // Extract the JWT token from the header
            username = jwtUtil.extractUsername(jwt); // Extract the username from the JWT token
        }

        // If username is not null and there's no authentication already in the context
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username); // Load UserDetails by username

            // Validate the token using the username (without roles)
            if (jwtUtil.validateToken(jwt, username)) {
                // Create the authentication token without roles (no authorities)
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()); // Note: no authorities for role-based checks
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)); // Set the details
                SecurityContextHolder.getContext().setAuthentication(authToken); // Set the authentication in the context
            }
        }

        // Proceed with the filter chain
        filterChain.doFilter(request, response);
    }
}
