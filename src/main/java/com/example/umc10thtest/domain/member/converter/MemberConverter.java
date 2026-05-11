package com.example.umc10thtest.domain.member.converter;

import com.example.umc10thtest.domain.member.dto.MemberResDTO;
import com.example.umc10thtest.domain.member.entity.Member;

public class MemberConverter {

    public static MemberResDTO.MyPageRes toMyPageRes(Member member) {
        return MemberResDTO.MyPageRes.builder()
                .name(member.getName())
                .email(member.getEmail())
                .profileUrl(member.getProfileUrl())
                .point(member.getPoint())
                .build();
    }
}
