package com.example.umc10th.domain.member.controller;

import com.example.umc10th.domain.member.dto.MemberReqDTO;
import com.example.umc10th.domain.member.dto.MemberResDTO;
import com.example.umc10th.domain.member.exception.code.MemberSuccessCode;
import com.example.umc10th.domain.member.service.MemberService;
import com.example.umc10th.global.apiPayload.ApiResponse;
import com.example.umc10th.global.apiPayload.code.BaseSuccessCode;
import com.example.umc10th.global.security.entity.AuthMember;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Member", description = "회원 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class MemberController {

    private final MemberService memberService;

    // 회원가입
    @Operation(summary = "회원가입", description = "이메일/비밀번호 기반 회원가입")
    @PostMapping("/auth/signup")
    public ApiResponse<MemberResDTO.SignUp> signUp(
            @RequestBody @Valid MemberReqDTO.SignUp dto
    ) {
        BaseSuccessCode code = MemberSuccessCode.SIGNUP_OK;
        return ApiResponse.success(code, memberService.signUp(dto));
    }

    // 로그인 → JWT 발급
    @Operation(summary = "로그인", description = "이메일/비밀번호로 로그인 후 JWT 액세스 토큰 발급")
    @PostMapping("/auth/login")
    public ApiResponse<MemberResDTO.Login> login(
            @RequestBody @Valid MemberReqDTO.Login dto
    ) {
        BaseSuccessCode code = MemberSuccessCode.LOGIN_OK;
        return ApiResponse.success(code, memberService.login(dto));
    }

    // 마이페이지 v2 - JWT 토큰에서 사용자 정보 자동 추출
    @Operation(summary = "마이페이지", description = "JWT 토큰으로 인증 후 내 프로필 정보 조회 (Authorization: Bearer {token})")
    @GetMapping("/users/me")
    public ApiResponse<MemberResDTO.GetInfo> getMyPage(
            @AuthenticationPrincipal AuthMember member
    ) {
        BaseSuccessCode code = MemberSuccessCode.MY_PAGE_OK;
        return ApiResponse.success(code, memberService.getInfo(member));
    }
}
