package com.user.service;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter  {

    private final JWTService jwtService;
    private final UserDetailsIMPL userDetailsIMPL;

    public JwtFilter(JWTService jwtService, UserDetailsIMPL userDetailsIMPL) {
        this.jwtService = jwtService;
        this.userDetailsIMPL = userDetailsIMPL;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        String jwtToken = null;
        String username = null;

        // Check Authorization header
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwtToken = authHeader.substring(7); // remove "Bearer "
            username = jwtService.extractUsername(jwtToken);
        }

        // If username exists and user not authenticated yet
        if (username != null &&
                SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails =
                    userDetailsIMPL.loadUserByUsername(username);

            // Validate token
            if (jwtService.validateToken(jwtToken, userDetails.getUsername())) {

                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );

                authToken.setDetails(
                        new WebAuthenticationDetailsSource()
                                .buildDetails(request)
                );

                // Set authentication
                SecurityContextHolder.getContext()
                        .setAuthentication(authToken);
            }
        }

        // Continue next filter
        filterChain.doFilter(request, response);
    }

}
