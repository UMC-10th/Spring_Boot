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
            @NotBlank(message = "이름은 필수입니다.") String name,
            @NotBlank(message = "닉네임은 필수입니다.") String nickname,
            @NotBlank(message = "이메일은 필수입니다.") @Email(message = "이메일 형식이 올바르지 않습니다.") String email,
            @NotBlank(message = "비밀번호는 필수입니다.") String password,
            @NotBlank(message = "전화번호는 필수입니다.") String phoneNumber,
            @NotBlank(message = "성별은 필수입니다.") String gender,
            @NotNull(message = "생년월일은 필수입니다.") LocalDate birth,
            @NotBlank(message = "주소는 필수입니다.") String address,
            @NotBlank(message = "상세 주소는 필수입니다.") String detailAddress,
            @NotEmpty(message = "선호 음식은 하나 이상 선택해야 합니다.") List<String> preferredFoods,
            @NotEmpty(message = "약관 동의는 하나 이상 필요합니다.") List<String> agreedTerms
    ) {}
}
