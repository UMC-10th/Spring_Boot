package com.example.umc10th.domain.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class MemberReqDTO {

    public record Join(
            @NotBlank(message = "이름은 필수입니다.")
            String name,

            @NotBlank(message = "이메일은 필수입니다.")
            @Email(message = "이메일 형식이 올바르지 않습니다.")
            String email,

            @NotBlank(message = "비밀번호는 필수입니다.")
            @Size(min = 8, message = "비밀번호는 최소 8자 이상이어야 합니다.")
            String password,

            String gender,
            LocalDate birthDate,
            String address
    ) {}

    public record Login(
            @NotBlank @Email String email,
            @NotBlank String password
    ) {}
}