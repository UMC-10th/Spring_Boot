package com.example.umc10th.domain.member.dto;

import com.example.umc10th.domain.member.enums.Gender;

import java.time.LocalDate;

public class MemberReqDTO {

    public record JoinDTO(
            String email,
            String password,
            String nickname,
            String phoneNumber,
            Gender gender,
            LocalDate birth
    ) {
    }
}