package com.example.umc10th.domain.member.controller;

import com.example.umc10th.domain.member.dto.MemberResDTO;
import com.example.umc10th.domain.member.exception.code.MemberSuccessCode;
import com.example.umc10th.domain.member.service.MemberService;
import com.example.umc10th.global.apiPayload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class MemberController {

    private final MemberService memberService;

    // 마이페이지
    @GetMapping("/me")
    public ApiResponse<MemberResDTO.GetInfo> getInfo(
    ) {
        return ApiResponse.onSuccess(
                MemberSuccessCode.MEMBER_GET_INFO_SUCCESS,
                memberService.getInfo()
        );
    }
}