package com.example.Spring_Boot.global.security.dto;

import lombok.Builder;

public class AuthResDTO {

    @Builder
    public record LoginResponse(
            String accessToken
    ) {
    }
}
