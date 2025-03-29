package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Auth.AuthResponse;
import com.example.demo.model.Auth.LoginRequest;
import com.example.demo.model.Auth.RefreshRequest;
import com.example.demo.security.JwtUtil;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtUtil jwtUtil;

    public AuthController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        String token = jwtUtil.createToken(request.getUsername());
        String refreshToken = jwtUtil.createRefreshToken(request.getUsername());

        var r = new AuthResponse();
        r.setToken(token);
        r.setRefreshToken(refreshToken);

        return ResponseEntity.ok(r);
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody RefreshRequest request) {
        if (!jwtUtil.isRefreshTokenValid(request.getRefreshToken())) {
            return ResponseEntity.badRequest().body("Invalid refresh token");
        }

        String username = jwtUtil.getUsernameFromRefreshToken(request.getRefreshToken());
        String newToken = jwtUtil.createToken(username);
        String newRefreshToken = jwtUtil.createRefreshToken(username);

        var r = new AuthResponse();
        r.setToken(newToken);
        r.setRefreshToken(newRefreshToken);

        return ResponseEntity.ok(r);
    }
}
