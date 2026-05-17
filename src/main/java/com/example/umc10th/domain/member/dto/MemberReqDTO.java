package com.example.umc10th.domain.member.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class MemberReqDTO {

    public record GetInfo(
            @NotNull(message = "회원 번호를 입력해주세요.")
            Long memberId
    ) {}

    // 회원가입
    public record SignUp(

            @NotNull(message = "이름을 입력해주세요.")
            String name,
            @NotNull(message = "성별을 입력해주세요.")
            String gender,
            @NotNull(message = "생년월일을 입력해주세요.")
            LocalDate birth,
            @NotNull(message = "주소를 입력해주세요.")
            String address,
            String detailAddress,
            @NotNull(message = "이메일을 입력해주세요.")
            String email,
            @NotNull(message = "전화번호를 입력해주세요.")
            String phoneNumber
    ) {}
}

