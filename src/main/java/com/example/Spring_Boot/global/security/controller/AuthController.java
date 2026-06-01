package com.example.Spring_Boot.global.security.controller;

import com.example.Spring_Boot.global.apiPayload.ApiResponse;
import com.example.Spring_Boot.global.apiPayload.code.GeneralSuccessCode;
import com.example.Spring_Boot.global.security.dto.AuthReqDTO;
import com.example.Spring_Boot.global.security.dto.AuthResDTO;
import com.example.Spring_Boot.global.security.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ApiResponse<AuthResDTO.LoginResponse> login(
            @Valid @RequestBody AuthReqDTO.LoginRequest request
    ) {
        return ApiResponse.onSuccess(
                GeneralSuccessCode.OK,
                authService.login(request)
        );
    }
}
