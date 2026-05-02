package com.example.Spring_Boot.domain.member.dto;

import com.example.Spring_Boot.domain.member.enums.Gender;

import java.time.LocalDate;
import java.util.List;

public class MemberResDTO {

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
