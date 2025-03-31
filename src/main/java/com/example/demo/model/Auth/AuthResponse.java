package com.example.demo.model.Auth;

import lombok.Data;

@Data
public class AuthResponse {
    private String token;
    private String refreshToken;
}
