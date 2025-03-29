package com.example.demo.model.Auth;

import lombok.Builder;
import lombok.Data;

@Data
public class AuthResponse {
    private String token;
    private String refreshToken;
}
