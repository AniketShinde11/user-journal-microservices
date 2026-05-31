package com.api_gateway.service;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter
        implements Filter {

    private final JWTService jwtService;

    public JwtAuthenticationFilter(
            JWTService jwtService) {

        this.jwtService = jwtService;
    }

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req =
                (HttpServletRequest) request;

        HttpServletResponse res =
                (HttpServletResponse) response;

        String path = req.getRequestURI();

        // public endpoints

        if(path.startsWith("/auth/login")
                || path.startsWith("/user/create-user")) {

            chain.doFilter(request,response);
            return;
        }

        String authHeader =
                req.getHeader("Authorization");

        if(authHeader == null
                || !authHeader.startsWith("Bearer ")) {

            res.setStatus(
                    HttpServletResponse.SC_UNAUTHORIZED
            );

            res.getWriter()
                    .write("Missing Token");

            return;
        }

        try {

            String token =
                    authHeader.substring(7);

            jwtService.validateToken(token);

            chain.doFilter(request,response);

        } catch (Exception e) {

            res.setStatus(
                    HttpServletResponse.SC_UNAUTHORIZED
            );

            res.getWriter()
                    .write("Invalid Token");
        }
    }
}
