package com.example.Spring_Boot.domain.member.converter;

import com.example.Spring_Boot.domain.member.dto.MemberReqDTO;
import com.example.Spring_Boot.domain.member.dto.MemberResDTO;
import com.example.Spring_Boot.domain.member.entity.Member;

import java.util.List;

public class MemberConverter {

    private MemberConverter() {
    }

    public static MemberResDTO.MyPageResponse toMyPageResponse(Member member) {
        return MemberResDTO.MyPageResponse.builder()
                .userId(member.getUserId())
                .name(member.getName())
                .nickname(member.getNickname())
                .email(member.getEmail())
                .phoneNumber(member.getPhoneNumber())
                .point(member.getPoint())
                .socialProvider(member.getSocialProvider().name())
                .build();
    }

    public static Member toMember(MemberReqDTO.CreateMemberRequest request, String encodedPassword) {
        return Member.builder()
                .email(request.email())
                .password(encodedPassword)
                .name(request.name())
                .nickname(request.nickname())
                .phoneNumber(request.phoneNumber())
                .gender(request.gender())
                .birth(request.birth())
                .address(request.address())
                .build();
    }

    public static MemberResDTO.CreateMemberResponse toCreateMemberResponse(
            Member member,
            List<Long> categoryIds
    ) {
        return MemberResDTO.CreateMemberResponse.builder()
                .userId(member.getUserId())
                .email(member.getEmail())
                .name(member.getName())
                .nickname(member.getNickname())
                .phoneNumber(member.getPhoneNumber())
                .gender(member.getGender())
                .birth(member.getBirth())
                .address(member.getAddress())
                .categoryIds(categoryIds)
                .build();
    }
}
