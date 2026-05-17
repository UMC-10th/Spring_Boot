package com.example.umc10th.domain.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public class MemberReqDTO {

    // 회원가입
    public record SignUp(
            @NotBlank String name,
            @NotBlank String nickname,
            @NotBlank @Email String email,
            @NotBlank String password,
            @NotBlank String phoneNumber,
            @NotBlank String gender,
            @NotNull LocalDate birth,
            @NotBlank String address,
            @NotBlank String detailAddress,
            @NotEmpty List<String> preferredFoods,
            @NotEmpty List<String> agreedTerms
    ) {}
}
