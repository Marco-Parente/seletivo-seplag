package com.example.demo.model.Auth;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
