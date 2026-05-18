package com.example.umc10th.domain.member.dto;

import jakarta.validation.constraints.*;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

public class MemberReqDTO {
    // 회원가입 요청
    @Builder
    public record SignUp(
            @NotBlank(message = "이름은 필수입니다.")
            @Size(min = 1, max = 50, message = "이름은 1자 이상 50자 이하로 작성해야 합니다.")
            String name,

            @NotBlank(message = "닉네임은 필수입니다.")
            @Size(min = 1, max = 30, message = "닉네임은 1자 이상 30자 이하로 작성해야 합니다.")
            String nickname,

            @NotBlank(message = "이메일은 필수입니다.")
            @Email(message = "이메일 형식이 올바르지 않습니다.")
            @Size(max = 50, message = "이메일은 최대 50자까지 작성할 수 있습니다.")
            String email,

            @NotBlank(message = "비밀번호는 필수입니다.")
            @Pattern(
                    regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*]).{8,20}$",
                    message = "비밀번호는 영문, 숫자, 특수문자를 포함한 8~20자여야 합니다."
            )
            String password,

            @NotBlank(message = "전화번호는 필수입니다.")
            @Pattern(
                    regexp = "^010\\d{8}$",
                    message = "전화번호는 010으로 시작하는 11자리 숫자여야 합니다."
            )
            String phoneNumber,

            @NotBlank(message = "성별은 필수입니다.")
            @Pattern(
                    regexp = "^(MALE|FEMALE|NONE)$",
                    message = "성별은 MALE, FEMALE, NONE 중 하나여야 합니다."
            )
            String gender, // "MALE", "FEMALE", "NONE"

            @NotNull(message = "생년월일은 필수입니다.")
            @Past(message = "생년월일은 과거 날짜여야 합니다.")
            LocalDate birth,

            @NotBlank(message = "주소는 필수입니다.")
            String address,

            @NotBlank(message = "상세주소는 필수입니다.")
            @Size(max = 255, message = "상세주소는 255자 이하여야 합니다.")
            String detailAddress,

            @NotEmpty(message = "선호 음식을 1개 이상 선택해야 합니다.")
            List<String> preferredFoods, // ["KOREAN", "JAPANESE"]

            @NotEmpty(message = "필수 약관 동의가 필요합니다.")
            List<String> agreedTerms // ["AGE", "SERVICE", "PRIVACY"]
    ) {}
}
