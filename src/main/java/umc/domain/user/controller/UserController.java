package umc.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import umc.domain.user.dto.request.UserRequestDto;
import umc.domain.user.dto.response.UserResponseDto;
import umc.global.apiPayload.ApiResponse;
import umc.global.apiPayload.code.status.GeneralSuccessCode;

import java.time.LocalDateTime;


@RestController
@RequiredArgsConstructor
public class UserController {

    // 회원가입
    @PostMapping("/auth/signup")
    public ApiResponse<UserResponseDto.SignUpResultDto> signUp(@RequestBody UserRequestDto.SignUpDto request) {
        UserResponseDto.SignUpResultDto dummyResponse = UserResponseDto.SignUpResultDto.builder()
                .userId(1L)
                .createdAt(LocalDateTime.now())
                .build();
        return ApiResponse.onSuccess(GeneralSuccessCode.OK, dummyResponse);
    }

    // 홈 화면
    @GetMapping("/home")
    public ApiResponse<UserResponseDto.HomeResultDto> getHome() {
        UserResponseDto.HomeResultDto dummyResponse = UserResponseDto.HomeResultDto.builder()
                .welcomeMessage("홈 화면")
                .build();
        return ApiResponse.onSuccess(GeneralSuccessCode.OK, dummyResponse);
    }
}
