package com.example.umc10th.domain.member.dto;

import java.time.LocalDate;

public class MemberReqDTO {

    // 마이페이지
    public record GetInfo(
            Long id
    ){}

    // 회원가입
    public record SignUp(
            String name,
            String email,
            String password,
            String gender,
            LocalDate birth,
            String address,
            String phoneNumber
    ) {}
}
