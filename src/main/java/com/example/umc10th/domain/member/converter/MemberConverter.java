package com.example.umc10th.domain.member.converter;

import com.example.umc10th.domain.member.dto.MemberResDTO;
import com.example.umc10th.domain.member.entity.Member;

public class MemberConverter {

    // 마이페이지
    public static MemberResDTO.GetInfo toGetInfo(Member member) {
        return MemberResDTO.GetInfo.builder()
                .memberId(member.getId())
                .name(member.getName())
                .address(member.getAddress().name())
                .detailAddress(member.getDetailAddress())
                .point(member.getPoint())
                .email(member.getEmail())
                .phoneNumber(member.getPhoneNumber())
                .profileUrl(member.getProfileUrl())
                .build();
    }

}
