package com.example.umc10thtest.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class MemberResDTO {

    @Getter
    @Builder
    @AllArgsConstructor
    public static class GetMemberRes {
        private String name;
        private String profileUrl;
        private String email;
        private String phoneNumber;
        private Integer point;
    }
}
