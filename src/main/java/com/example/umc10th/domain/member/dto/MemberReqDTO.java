package com.example.umc10th.domain.member.dto;

import java.time.LocalDate;

public class MemberReqDTO {

    public record GetInfo(
            Long memberId
    ) {}

    // 회원가입
    public record SignUp(
            String name,
            String gender,
            LocalDate birth,
            String address,
            String detailAddress,
            String email,
            String phoneNumber
    ) {}
}

