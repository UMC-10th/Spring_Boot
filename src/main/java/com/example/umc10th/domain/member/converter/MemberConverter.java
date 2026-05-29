package com.example.umc10th.domain.member.converter;

import com.example.umc10th.domain.member.dto.MemberReqDTO;
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

    // 회원가입
    public static Member toMember(MemberReqDTO.SignUp dto, String encodedPassword) {
        return Member.builder()
                .name(dto.name())
                .gender(dto.gender())
                .birth(dto.birth())
                .address(dto.address())
                .detailAddress(dto.detailAddress())
                .email(dto.email())
                .password(encodedPassword)
                .phoneNumber(dto.phoneNumber())
                .build();
    }

    public static MemberResDTO.SignUp toSignUp(Member member) {
        return MemberResDTO.SignUp.builder()
                .memberId(member.getId())
                .name(member.getName())
                .email(member.getEmail())
                .createdAt(member.getCratedAt())
                .build();
    }
}
