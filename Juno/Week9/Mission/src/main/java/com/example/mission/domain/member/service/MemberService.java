package com.example.mission.domain.member.service;

import com.example.mission.domain.member.converter.MemberConverter;
import com.example.mission.domain.member.dto.MemberReqDTO;
import com.example.mission.domain.member.entity.Member;
import com.example.mission.domain.member.exception.MemberException;
import com.example.mission.domain.member.exception.code.MemberErrorCode;
import com.example.mission.domain.member.repository.MemberRepository;
import com.example.mission.global.security.util.JwtUtil;
import com.example.mission.service.auth.AuthMember;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional
    public Member joinMember(MemberReqDTO.JoinDTO request) {
        // 이미 가입된 이메일인지 확인하는 로직 등은 생략 (최소한으로 구현)
        
        Member member = Member.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .gender(request.getGender())
                .address(request.getAddress())
                .role("ROLE_USER")
                .build();

        return memberRepository.save(member);
    }

    public String login(MemberReqDTO.LoginDTO request) {
        Member member = memberRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        if (!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
            throw new MemberException(MemberErrorCode.UNAUTHORIZED); // or a specific BAD_PASSWORD error
        }

        return jwtUtil.createAccessToken(new AuthMember(member));
    }

    public Member getMember(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));
    }
}
