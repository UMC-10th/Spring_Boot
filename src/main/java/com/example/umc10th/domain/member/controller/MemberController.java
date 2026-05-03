package com.example.umc10th.domain.member.controller;

import com.example.umc10th.domain.member.dto.MemberReqDTO;
import com.example.umc10th.domain.member.dto.MemberResDTO;
import com.example.umc10th.domain.member.exception.code.MemberSuccessCode;
import com.example.umc10th.domain.member.service.MemberService;
import com.example.umc10th.global.apiPayload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class MemberController {

    private final MemberService memberService;

    // 회원가입
    @PostMapping("")
    public ApiResponse<MemberResDTO.SignUp> signUp(
            @RequestBody MemberReqDTO.SignUp dto
    ) {
        return ApiResponse.onSuccess(
                MemberSuccessCode.MEMBER_SIGNUP_SUCCESS,
                memberService.signUp(dto)
        );
    }

    // 마이페이지
    @PostMapping("/me")
    public ApiResponse<MemberResDTO.GetInfo> getInfo(
            @RequestBody MemberReqDTO.GetInfo dto
    ) {
        return ApiResponse.onSuccess(
                MemberSuccessCode.MEMBER_GET_INFO_SUCCESS,
                memberService.getInfo(dto)
        );
    }
}