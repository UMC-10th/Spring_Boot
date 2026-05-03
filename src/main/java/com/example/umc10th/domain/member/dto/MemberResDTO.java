package com.example.umc10th.domain.member.dto;

import lombok.Builder;

public class MemberResDTO {

    // 마이페이지 응답
    @Builder
    public record GetInfo(
            String name,
            String profileUrl,
            String email,
            String phoneNumber,
            Integer point
    ){}

    // 회원가입 응답
    @Builder
    public record SignUp(
            Long memberId
    ) {}
}
