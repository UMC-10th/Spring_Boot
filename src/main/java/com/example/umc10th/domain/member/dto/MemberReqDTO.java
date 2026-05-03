package com.example.umc10th.domain.member.dto;

import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

public class MemberReqDTO {
    // 회원가입 요청
    @Builder
    public record SignUp(
            String name,
            String nickname,
            String email,
            String password,
            String phoneNumber,
            String gender,                // "MALE", "FEMALE", "NONE"
            LocalDate birth,
            String address,
            String detailAddress,
            List<String> preferredFoods,  // ["KOREAN", "JAPANESE"]
            List<String> agreedTerms      // ["AGE", "SERVICE", "PRIVACY"]
    ) {}
}
