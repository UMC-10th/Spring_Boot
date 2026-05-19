package com.example.Spring_Boot.domain.user.controller;

import com.example.Spring_Boot.domain.user.dto.UserReqDTO;
import com.example.Spring_Boot.domain.user.dto.UserResDTO;
import com.example.Spring_Boot.domain.user.service.UserService;
import com.example.Spring_Boot.global.apiPayload.ApiResponse;
import com.example.Spring_Boot.global.apiPayload.code.GeneralSuccessCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class UserController {

    private final UserService userService;

    // Public API - 회원가입
    @PostMapping("/users")
    public ApiResponse<UserResDTO.JoinResultDTO> join(
            @Valid @RequestBody UserReqDTO.JoinDTO request
    ) {
        return ApiResponse.onSuccess(GeneralSuccessCode.CREATED, userService.join(request));
    }

    // Private API - 마이페이지
    @GetMapping("/users/{userId}")
    public ApiResponse<UserResDTO.MyPageDTO> getMyPage(
            @PathVariable Long userId
    ) {
        return ApiResponse.onSuccess(GeneralSuccessCode.OK, userService.getMyPage(userId));
    }
}