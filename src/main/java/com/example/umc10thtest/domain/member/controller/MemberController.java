package com.example.umc10thtest.domain.member.controller;

import com.example.umc10thtest.domain.member.dto.MemberReqDTO;
import com.example.umc10thtest.domain.member.dto.MemberResDTO;
import com.example.umc10thtest.domain.member.exception.code.MemberSuccessCode;
import com.example.umc10thtest.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "Member", description = "회원 관련 API")
public class MemberController {

    @PostMapping("/me")
    @Operation(summary = "유저 조회", description = "ID로 유저 정보를 조회합니다.")
    public ApiResponse<MemberResDTO.GetMemberRes> getMember(
            @RequestBody MemberReqDTO.GetMemberReq request
    ) {
        MemberResDTO.GetMemberRes result = MemberResDTO.GetMemberRes.builder()
                .name("nickname012")
                .profileUrl("https://example.com/profile.jpg")
                .email("user@example.com")
                .phoneNumber(null)
                .point(2500)
                .build();

        return ApiResponse.onSuccess(MemberSuccessCode.GET_MEMBER, result);
    }
}
