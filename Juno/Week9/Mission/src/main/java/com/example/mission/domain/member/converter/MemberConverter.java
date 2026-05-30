package com.example.mission.domain.member.converter;

import com.example.mission.domain.member.dto.MemberReqDTO;
import com.example.mission.domain.member.dto.MemberResDTO;
import com.example.mission.domain.member.dto.OAuthDTO;
import com.example.mission.domain.member.entity.Member;

import java.time.LocalDateTime;
import java.util.UUID;

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

    public static Member toMember(OAuthDTO dto) {
        return Member.builder()
                .email(dto.getEmail())
                .password(UUID.randomUUID().toString()) // 소셜 로그인은 비밀번호가 필요 없으므로 랜덤값
                .name(dto.getName())
                .role("ROLE_USER")
                .socialType(dto.getSocialType())
                .socialUid(dto.getSocialUid())
                .build();
    }

    public static MemberResDTO.Login toLogin(String token) {
        return MemberResDTO.Login.builder()
                .accessToken(token)
                .build();
    }

    public static MemberResDTO.MemberDTO toMemberDTO(Member member) {
        return MemberResDTO.MemberDTO.builder()
                .memberId(member.getId())
                .email(member.getEmail())
                .name(member.getName())
                .gender(member.getGender())
                .address(member.getAddress())
                .build();
    }
}
