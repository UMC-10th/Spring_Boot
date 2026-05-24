package com.example.umc10th.domain.member.converter;

import com.example.umc10th.domain.member.dto.MemberReqDTO;
import com.example.umc10th.domain.member.dto.MemberResDTO;
import com.example.umc10th.domain.member.entity.Member;

public class MemberConverter {

    public static Member toMember(MemberReqDTO.JoinDTO request, String encodedPassword) {
        return Member.builder()
                .email(request.email())
                .password(encodedPassword)
                .nickname(request.nickname())
                .phoneNumber(request.phoneNumber())
                .gender(request.gender())
                .birth(request.birth())
                .point(0)
                .build();
    }

    public static MemberResDTO.JoinResultDTO toJoinResultDTO(Member member) {
        return MemberResDTO.JoinResultDTO.builder()
                .memberId(member.getId())
                .email(member.getEmail())
                .nickname(member.getNickname())
                .createdAt(member.getCreatedAt())
                .build();
    }

    public static MemberResDTO.MemberInfoDTO toMemberInfoDTO(Member member) {
        return MemberResDTO.MemberInfoDTO.builder()
                .memberId(member.getId())
                .email(member.getEmail())
                .nickname(member.getNickname())
                .phoneNumber(member.getPhoneNumber())
                .gender(member.getGender())
                .birth(member.getBirth())
                .point(member.getPoint())
                .createdAt(member.getCreatedAt())
                .build();
    }
}