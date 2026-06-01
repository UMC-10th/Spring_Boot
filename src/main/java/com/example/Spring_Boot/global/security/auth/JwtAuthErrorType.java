package com.example.Spring_Boot.global.security.auth;

public enum JwtAuthErrorType {

    INVALID_TOKEN,
    EXPIRED_TOKEN;

    public static final String REQUEST_ATTRIBUTE = "jwtAuthError";
}
