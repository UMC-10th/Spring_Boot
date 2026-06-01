package com.example.Spring_Boot.domain.member.controller;

import com.example.Spring_Boot.domain.member.dto.MemberReqDTO;
import com.example.Spring_Boot.domain.member.dto.MemberResDTO;
import com.example.Spring_Boot.domain.member.exception.code.MemberSuccessCode;
import com.example.Spring_Boot.domain.member.service.MemberService;
import com.example.Spring_Boot.global.apiPayload.ApiResponse;
import com.example.Spring_Boot.global.security.auth.AuthMember;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/me")
    public ApiResponse<MemberResDTO.MyPageResponse> getMyPage(
            @AuthenticationPrincipal AuthMember authMember
    ) {
        MemberResDTO.MyPageResponse response = memberService.getMyPage(authMember);

        return ApiResponse.onSuccess(
                MemberSuccessCode.MEMBER_MY_PAGE_OK,
                response
        );
    }

    @PostMapping
    public ApiResponse<MemberResDTO.CreateMemberResponse> createMember(
            @Valid @RequestBody MemberReqDTO.CreateMemberRequest request
    ) {
        MemberResDTO.CreateMemberResponse response = memberService.createMember(request);

        return ApiResponse.onSuccess(
                MemberSuccessCode.MEMBER_CREATED,
                response
        );
    }
}
