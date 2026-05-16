package com.example.umc10th.domain.member.controller;


import com.example.umc10th.domain.member.dto.MemberReqDTO;
import com.example.umc10th.domain.member.dto.MemberResDTO;
import com.example.umc10th.domain.member.exception.code.MemberSuccessCode;
import com.example.umc10th.domain.member.service.MemberService;
import com.example.umc10th.global.apiPayload.ApiResponse;
import com.example.umc10th.global.apiPayload.code.BaseSuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/users/me")
    public ApiResponse<MemberResDTO.GetInfo> getInfo(
            @RequestBody MemberReqDTO.GetInfo dto
    ) {
        BaseSuccessCode code = MemberSuccessCode.OK;
        return ApiResponse.success(code, memberService.getInfo(dto));
    }

    // 마이페이지
    // TODO: Security 적용 후 매개변수 변경
    @GetMapping("/myPage")
    public ApiResponse<MemberResDTO.GetInfo> getMyPage(
            @RequestParam Long memberId
    ) {
        BaseSuccessCode code = MemberSuccessCode.MY_PAGE_OK;
        return ApiResponse.success(code, memberService.getMyPage(memberId));
    }

    // 회원가입
    @PostMapping("/auth/signup")
    public ApiResponse<MemberResDTO.SignUp> signUp(
            @RequestBody MemberReqDTO.SignUp dto
    ) {
        BaseSuccessCode code = MemberSuccessCode.SIGNUP_OK;
        return ApiResponse.success(code, memberService.signUp(dto));
    }
}

