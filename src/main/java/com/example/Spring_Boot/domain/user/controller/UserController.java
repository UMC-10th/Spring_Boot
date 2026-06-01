package com.example.Spring_Boot.domain.user.controller;

import com.example.Spring_Boot.domain.user.dto.UserReqDTO;
import com.example.Spring_Boot.domain.user.dto.UserResDTO;
import com.example.Spring_Boot.domain.user.security.AuthMember;
import com.example.Spring_Boot.domain.user.service.UserService;
import com.example.Spring_Boot.global.apiPayload.ApiResponse;
import com.example.Spring_Boot.global.apiPayload.code.GeneralSuccessCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class UserController {

    private final UserService userService;

    @PostMapping("/users")
    public ApiResponse<UserResDTO.JoinResultDTO> join(
            @Valid @RequestBody UserReqDTO.JoinDTO request
    ) {
        return ApiResponse.onSuccess(GeneralSuccessCode.CREATED, userService.join(request));
    }

    @PostMapping("/login")
    public ApiResponse<UserResDTO.LoginResultDTO> login(
            @Valid @RequestBody UserReqDTO.LoginDTO request
    ) {
        return ApiResponse.onSuccess(GeneralSuccessCode.OK, userService.login(request));
    }

    @GetMapping("/users/me")
    public ApiResponse<UserResDTO.MyPageDTO> getMyPage(
            @AuthenticationPrincipal AuthMember authMember
    ) {
        return ApiResponse.onSuccess(GeneralSuccessCode.OK, userService.getMyPage(authMember));
    }
}