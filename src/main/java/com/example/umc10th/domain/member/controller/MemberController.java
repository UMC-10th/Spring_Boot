package com.example.umc10th.domain.member.controller;

import com.example.umc10th.domain.member.dto.MemberReqDTO;
import com.example.umc10th.domain.member.dto.MemberResDTO;
import com.example.umc10th.domain.member.exception.code.MemberSuccessCode;
import com.example.umc10th.domain.member.service.MemberService;
import com.example.umc10th.global.apiPayload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class MemberController {

    private final MemberService memberService;

    // 1. 회원가입
    @PostMapping("/auth/signup")
    public ApiResponse<MemberResDTO.SignUp> signUp(
            @RequestBody MemberReqDTO.SignUp request
    ) {
        // 6주차에서 memberService.signUp(request)로 교체
        MemberResDTO.SignUp result = MemberResDTO.SignUp.builder()
                .memberId(1L)
                .nickname(request.nickname())
                .email(request.email())
                .build();

        return ApiResponse.onSuccess(MemberSuccessCode.SIGN_UP, result);
    }

    // 2. 홈 화면 조회
    @GetMapping("/home")
    public ApiResponse<MemberResDTO.Home> getHome(@RequestParam Long memberId) { // @RequestParam: URL 뒤에 붙는 쿼리 파라미터를 받을 때 쓴다.
        MemberResDTO.Home result = memberService.getHome(memberId);
        return ApiResponse.onSuccess(MemberSuccessCode.GET_HOME, result);
    }

    // 3. 마이페이지 조회
    @GetMapping("/members/me")
    public ApiResponse<MemberResDTO.MyPage> getMyPage(@RequestParam Long memberId) {
        MemberResDTO.MyPage result = memberService.getMyPage(memberId);
        return ApiResponse.onSuccess(MemberSuccessCode.GET_MY_PAGE, result);
    }
}
