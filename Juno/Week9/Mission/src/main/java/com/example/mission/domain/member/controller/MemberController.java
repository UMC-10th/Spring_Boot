package com.example.mission.domain.member.controller;

import com.example.mission.domain.member.converter.MemberConverter;
import com.example.mission.domain.member.dto.MemberReqDTO;
import com.example.mission.domain.member.dto.MemberResDTO;
import com.example.mission.domain.member.entity.Member;
import com.example.mission.domain.member.service.MemberService;
import com.example.mission.global.apiPayload.ApiResponse;
import com.example.mission.global.apiPayload.code.GeneralSuccessCode;
import com.example.mission.service.auth.AuthMember;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/join")
    @Operation(summary = "회원 가입 API", description = "회원 가입을 위한 API이며, email, password 등을 입력받습니다.")
    public ApiResponse<MemberResDTO.JoinResultDTO> join(@RequestBody MemberReqDTO.JoinDTO request) {
        Member member = memberService.joinMember(request);
        return ApiResponse.onSuccess(GeneralSuccessCode.OK, MemberConverter.toJoinResultDTO(member));
    }

    @PostMapping("/login")
    @Operation(summary = "로그인 API", description = "이메일과 비밀번호를 이용해 로그인하고 JWT 토큰을 발급받습니다.")
    public ApiResponse<MemberResDTO.Login> login(@RequestBody MemberReqDTO.LoginDTO request) {
        String token = memberService.login(request);
        return ApiResponse.onSuccess(GeneralSuccessCode.OK, MemberConverter.toLogin(token));
    }

    @GetMapping("/me")
    @Operation(summary = "마이페이지 조회 API", description = "JWT 토큰을 이용해 현재 로그인한 사용자의 정보를 조회합니다.")
    public ApiResponse<MemberResDTO.MemberDTO> getMe(@AuthenticationPrincipal AuthMember authMember) {
        // AuthMember가 null인 경우는 필터에서 이미 처리되거나 SecurityConfig에서 막힘
        Member member = authMember.getMember();
        return ApiResponse.onSuccess(GeneralSuccessCode.OK, MemberConverter.toMemberDTO(member));
    }
}
