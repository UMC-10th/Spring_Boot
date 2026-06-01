package com.example.Spring_Boot.domain.member.converter;

import com.example.Spring_Boot.domain.member.dto.MemberReqDTO;
import com.example.Spring_Boot.domain.member.dto.MemberResDTO;
import com.example.Spring_Boot.domain.member.entity.Member;
import com.example.Spring_Boot.domain.member.enums.SocialType;
import com.example.Spring_Boot.global.security.oauth.dto.OAuthDTO;

import java.time.LocalDate;
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

    public static Member toMember(OAuthDTO dto) {
        return Member.builder()
                .email(dto.email())
                .name(limitLength(dto.nickname(), 5))
                .nickname(limitLength(dto.nickname(), 15))
                .phoneNumber("소셜로그인")
                .birth(LocalDate.of(1900, 1, 1))
                .address("소셜로그인")
                .socialProvider(dto.provider())
                .socialUid(dto.socialUid())
                .build();
    }

    private static String limitLength(String value, int maxLength) {
        if (value == null || value.isBlank()) {
            return "소셜유저";
        }

        return value.length() <= maxLength ? value : value.substring(0, maxLength);
    }
}
