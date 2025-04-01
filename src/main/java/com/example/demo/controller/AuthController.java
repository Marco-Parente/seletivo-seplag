package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Auth.AuthResponse;
import com.example.demo.model.Auth.LoginRequest;
import com.example.demo.model.Auth.RefreshRequest;
import com.example.demo.model.Auth.User;
import com.example.demo.security.JwtUtil;
import com.example.demo.service.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    public AuthController(JwtUtil jwtUtil, AuthenticationManager authenticationManager, UserService userService) {
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        String token = jwtUtil.createToken(authentication.getName());
        String refreshToken = jwtUtil.createRefreshToken(authentication.getName());

        AuthResponse response = new AuthResponse();
        response.setToken(token);
        response.setRefreshToken(refreshToken);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody RefreshRequest request) {
        if (!jwtUtil.isRefreshTokenValid(request.getRefreshToken())) {
            return ResponseEntity.badRequest().body("Invalid refresh token");
        }

        String username = jwtUtil.getUsernameFromRefreshToken(request.getRefreshToken());
        String newToken = jwtUtil.createToken(username);
        String newRefreshToken = jwtUtil.createRefreshToken(username);

        AuthResponse response = new AuthResponse();
        response.setToken(newToken);
        response.setRefreshToken(newRefreshToken);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody LoginRequest request) {
        User user = userService.createUser(request.getUsername(), request.getPassword());
        return ResponseEntity.ok(user);
    }
}
