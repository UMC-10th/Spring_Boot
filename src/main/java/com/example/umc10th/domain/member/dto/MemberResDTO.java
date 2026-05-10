package com.example.umc10th.domain.member.dto;

public class MemberResDTO {

    // 마이페이지 응답
    public record GetInfo(
            Long memberId,
            String nickname,
            String email,
            String phoneNumber,
            Integer point,
            String profileUrl
    ){}

    // 회원가입 응답
    public record SignUp(
            Long memberId,
            String nickname,
            String email
    ) {}
}
