package com.example.umc10th.domain.member.dto;

import com.example.umc10th.domain.member.enums.Address;
import com.example.umc10th.domain.member.enums.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
            Gender gender,
            @NotNull(message = "생년월일을 입력해주세요.")
            LocalDate birth,
            @NotNull(message = "주소를 입력해주세요.")
            Address address,
            String detailAddress,
            @Email(message = "올바른 이메일 형식을 입력해주세요.")
            @NotNull(message = "이메일을 입력해주세요.")
            String email,
            @NotNull(message = "비밀번호를 입력해주세요.")
            String password,
            @NotNull(message = "전화번호를 입력해주세요.")
            String phoneNumber
    ) {}

    // 로그인
    public record Login(
            @Email(message = "올바른 이메일 형식을 입력해주세요.")
            @NotBlank(message = "이메일을 입력해주세요.")
            String email,
            @NotBlank(message = "비밀번호를 입력해주세요.")
            String password
    ) {}
}
