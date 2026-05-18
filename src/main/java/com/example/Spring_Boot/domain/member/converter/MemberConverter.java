package com.example.Spring_Boot.domain.member.converter;

import com.example.Spring_Boot.domain.member.dto.MemberResDTO;
import com.example.Spring_Boot.domain.member.entity.Member;

public class MemberConverter {

    private MemberConverter() {
    }

    public static MemberResDTO.MyPageResponse toMyPageResponse(Member member) {
        return MemberResDTO.MyPageResponse.builder()
                .userId(member.getUserId())
                .name(member.getName())
                .nickname(member.getNickname())
                .email(null)
                .phoneNumber(member.getPhoneNumber())
                .point(member.getPoint())
                .socialProvider(member.getSocialProvider().name())
                .build();
    }
}
