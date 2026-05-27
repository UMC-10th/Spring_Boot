package com.example.mission.domain.member.converter;

import com.example.mission.domain.member.dto.MemberReqDTO;
import com.example.mission.domain.member.dto.MemberResDTO;
import com.example.mission.domain.member.entity.Member;

import java.time.LocalDateTime;

public class MemberConverter {

    public static MemberResDTO.JoinResultDTO toJoinResultDTO(Member member) {
        return MemberResDTO.JoinResultDTO.builder()
                .memberId(member.getId())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static Member toMember(MemberReqDTO.JoinDTO request) {
        return Member.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .name(request.getName())
                .gender(request.getGender())
                .address(request.getAddress())
                .role("ROLE_USER")
                .build();
    }
}
