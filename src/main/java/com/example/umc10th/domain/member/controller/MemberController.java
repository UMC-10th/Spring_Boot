package com.example.umc10th.domain.member.controller;

import com.example.umc10th.domain.member.dto.MemberReqDTO;
import jakarta.validation.Valid;
import com.example.umc10th.domain.member.dto.MemberResDTO;
import com.example.umc10th.domain.member.service.MemberService;
import com.example.umc10th.domain.member.exception.code.MemberSuccessCode;
import com.example.umc10th.global.apiPayload.ApiResponse;
import com.example.umc10th.global.apiPayload.code.GeneralSuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class MemberController {

    private final MemberService memberService;

    // 1) 회원가입
    @PostMapping("/auth/signup")
    public ApiResponse<MemberResDTO.SignUp> signUp(
            @RequestBody @Valid MemberReqDTO.SignUp dto
    ) {
        return ApiResponse.onSuccess(
                MemberSuccessCode.MEMBER_SIGNUP_SUCCESS,
                memberService.signUp(dto)
        );
    }

    // 2) 홈 화면 조회
    @GetMapping("/home")
    public ApiResponse<MemberResDTO.Home> getHome(
            @RequestParam(required = false) Long locationId,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size
    ) {
        return ApiResponse.onSuccess(
                GeneralSuccessCode.OK,
                memberService.getHome(locationId, page, size)
        );
    }

    // 3) 마이페이지 조회
    @GetMapping("/members/me")
    public ApiResponse<MemberResDTO.MyPage> getMyPage() {
        return ApiResponse.onSuccess(
                MemberSuccessCode.MEMBER_GET_INFO_SUCCESS,
                memberService.getMyPage()
        );
    }
}