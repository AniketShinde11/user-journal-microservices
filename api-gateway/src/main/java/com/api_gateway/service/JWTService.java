package com.api_gateway.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
public class JWTService {

    @Value("${jwt.key}")
    private String SECRET_KEY;

    private SecretKey getSignKey() {
        return Keys.hmacShaKeyFor(
                SECRET_KEY.getBytes()
        );
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public boolean validateToken(String token) {
        return !isTokenExpired(token);
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token)
                .before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(
                token,
                Claims::getExpiration
        );
    }

    public <T> T extractClaim(
            String token,
            Function<Claims, T> resolver) {

        Claims claims =
                Jwts.parser()
                        .verifyWith(getSignKey())
                        .build()
                        .parseSignedClaims(token)
                        .getPayload();

        return resolver.apply(claims);
    }

}
