package com.example.umc10th.domain.member.dto;

import com.example.umc10th.domain.member.enums.Gender;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class MemberResDTO {

    @Builder
    public record JoinResultDTO(
            Long memberId,
            String email,
            String nickname,
            LocalDateTime createdAt
    ) {
    }

    @Builder
    public record MemberInfoDTO(
            Long memberId,
            String email,
            String nickname,
            String phoneNumber,
            Gender gender,
            LocalDate birth,
            Integer point,
            LocalDateTime createdAt
    ) {
    }
}