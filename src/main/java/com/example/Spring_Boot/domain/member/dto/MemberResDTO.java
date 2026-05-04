package com.example.Spring_Boot.domain.member.dto;

import com.example.Spring_Boot.domain.member.enums.Gender;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

public class MemberResDTO {

    @Builder
    public record CreateMemberResponse(
            Long userId,
            String name,
            Gender gender,
            LocalDate birth,
            String address,
            List<Long> categoryIds
    ) {
    }
}
