package umc.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import umc.domain.user.dto.request.UserRequestDto;
import umc.domain.user.dto.response.UserResponseDto;
import umc.domain.user.entity.User;
import umc.global.apiPayload.ApiResponse;
import umc.global.apiPayload.code.status.GeneralSuccessCode;
import umc.global.security.entity.AuthUser; // JWT 인증 객체 임포트 추가

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class UserController {

    // 1. 회원가입
    @PostMapping("/sign-up")
    public ApiResponse<UserResponseDto.SignUpResultDto> signUp(@RequestBody UserRequestDto.SignUpDto request) {
        UserResponseDto.SignUpResultDto dummyResponse = UserResponseDto.SignUpResultDto.builder()
                .userId(1L)
                .createdAt(LocalDateTime.now())
                .build();
        return ApiResponse.onSuccess(GeneralSuccessCode.OK, dummyResponse);
    }

    // 2. 홈 화면
    @GetMapping("/home")
    public ApiResponse<UserResponseDto.HomeResultDto> getHome() {
        UserResponseDto.HomeResultDto dummyResponse = UserResponseDto.HomeResultDto.builder()
                .build();
        return ApiResponse.onSuccess(GeneralSuccessCode.OK, dummyResponse);
    }

    @GetMapping("/me")
    public ApiResponse<UserResponseDto.HomeResultDto> getMyInfo(
            @AuthenticationPrincipal AuthUser authUser
    ) {
        User user = authUser.getUser();

        UserResponseDto.HomeResultDto result = UserResponseDto.HomeResultDto.builder()
                .name(user.getName())
                .email(user.getEmail())
                .build();

        return ApiResponse.onSuccess(GeneralSuccessCode.OK, result);
    }
}