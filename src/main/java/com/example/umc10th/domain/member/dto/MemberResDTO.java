package com.example.umc10th.domain.member.dto;

import lombok.Builder;

import java.time.LocalDateTime;

public class MemberResDTO {

    // 마이페이지 응답
    @Builder
    public record GetInfo(
            Long memberId,
            String name,
            String email,
            String phoneNumber,
            String address,
            String detailAddress,
            String profileUrl,
            Integer point
    ) {}

    // 회원가입 응답
    @Builder
    public record SignUp(
            Long memberId,
            String name,
            String email,
            LocalDateTime createdAt
    ) {}
}

