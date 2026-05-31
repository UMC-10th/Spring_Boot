package com.example.umc10th.global.security.service;

import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.member.enums.SocialType;
import com.example.umc10th.domain.member.exception.MemberException;
import com.example.umc10th.domain.member.exception.code.MemberErrorCode;
import com.example.umc10th.domain.member.repository.MemberRepository;
import com.example.umc10th.global.security.entity.AuthMember;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService {

    private final MemberRepository memberRepository;

    /**
     * memberId로 회원 조회 후 인증 객체 반환
     * JwtAuthFilter에서 토큰 검증 후 호출됨 (일반/OAuth 공통)
     *
     * @param memberId JWT subject에 담긴 회원 PK
     */
    public UserDetails loadUserById(Long memberId) throws UsernameNotFoundException {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));
        return new AuthMember(member);
    }

    /**
     * 소셜 로그인 타입 + UID로 회원 조회 (CustomOAuthService에서 사용)
     *
     * @param socialType 소셜 로그인 타입 (KAKAO 등)
     * @param uid        소셜 로그인 고유 ID
     */
    public UserDetails loadUserByUidAndSocialType(
            SocialType socialType,
            String uid
    ) throws UsernameNotFoundException {
        Member member = memberRepository.findBySocialTypeAndSocialUid(socialType, uid)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));
        return new AuthMember(member);
    }
}
