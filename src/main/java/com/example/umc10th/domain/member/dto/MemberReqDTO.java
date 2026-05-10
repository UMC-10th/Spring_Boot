package com.example.umc10th.domain.member.dto;

import java.time.LocalDate;
import java.util.List;

public class MemberReqDTO {

    // 회원가입
    public record SignUp(
            String name,
            String nickname,
            String email,
            String password,
            String phoneNumber,
            String gender,
            LocalDate birth,
            String address,
            String detailAddress,
            List<String> preferredFoods,
            List<String> agreedTerms
    ) {}
}
