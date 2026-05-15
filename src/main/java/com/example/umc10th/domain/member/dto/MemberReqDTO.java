package com.example.umc10th.domain.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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

    /**
     * 내 진행중인 미션 조회 요청 Body.
     * JWT 인증 도입 전 임시로 사용자 ID를 Body로 전달받는다.
     * (추후 토큰에서 추출하도록 교체 예정)
     */
    public record MyMissions(
            @NotNull(message = "사용자 ID는 필수입니다.")
            @Positive(message = "사용자 ID는 양수여야 합니다.")
            Long memberId
    ) {}
}