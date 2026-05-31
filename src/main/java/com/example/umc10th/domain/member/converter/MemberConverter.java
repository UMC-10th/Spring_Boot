package com.example.umc10th.domain.member.converter;

import com.example.umc10th.domain.member.dto.MemberReqDTO;
import com.example.umc10th.domain.member.dto.MemberResDTO;
import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.member.enums.Address;
import com.example.umc10th.global.security.dto.OAuthDTO;

import java.time.LocalDate;

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

    // 일반 회원가입 (이메일/비밀번호)
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

    // OAuth 회원가입 (소셜 로그인 신규 유저)
    // - 비밀번호/생년월일/주소 등 OAuth에서 제공하지 않는 정보는 기본값 처리
    public static Member toMember(OAuthDTO dto) {
        return Member.builder()
                .name(dto.getName())
                .email(dto.getSocialEmail())
                .socialType(dto.getSocialType())
                .socialUid(dto.getSocialUid())
                .password("")               // OAuth 유저는 비밀번호 없음
                .address(Address.NONE)      // 추후 프로필 설정에서 입력
                .detailAddress("")          // 추후 프로필 설정에서 입력
                .birth(LocalDate.of(1900, 1, 1)) // 추후 프로필 설정에서 입력
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

    // OAuth 로그인 성공 응답 DTO
    public static MemberResDTO.Login toLogin(String accessToken) {
        return MemberResDTO.Login.builder()
                .accessToken(accessToken)
                .build();
    }
}

