package com.example.demo.security;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;

@Component
public class JwtUtil {
    private final long jwtExpirationInMs = 1000 * 60 * 5;
    private final long refreshExpirationInMs = 1000 * 60 * 60 * 6;
    private SecretKey jwtKey;
    private SecretKey refreshKey;

    @PostConstruct
    private void init() {
        jwtKey = Jwts.SIG.HS256.key().build();
        refreshKey = Jwts.SIG.HS256.key().build();
    }

    public String createToken(String username) {
        return Jwts.builder()
                .subject(username).issuedAt(new Date())
                .expiration(new Date(new Date().getTime() + jwtExpirationInMs))
                .signWith(jwtKey)
                .compact();
    }

    public String createRefreshToken(String username) {
        return Jwts.builder()
                .subject(username).issuedAt(new Date())
                .expiration(new Date(new Date().getTime() + refreshExpirationInMs))
                .signWith(jwtKey)
                .compact();
    }

    public boolean isTokenValid(String token) {
        try {
            Jwts.parser().verifyWith(jwtKey).build().parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isRefreshTokenValid(String token) {
        try {
            Jwts.parser().verifyWith(refreshKey).build().parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parser().verifyWith(jwtKey).build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public String getUsernameFromRefreshToken(String token) {
        return Jwts.parser().verifyWith(refreshKey).build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

}
