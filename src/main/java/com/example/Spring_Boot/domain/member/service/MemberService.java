package com.example.Spring_Boot.domain.member.service;

import com.example.Spring_Boot.domain.member.converter.MemberConverter;
import com.example.Spring_Boot.domain.member.dto.MemberReqDTO;
import com.example.Spring_Boot.domain.member.dto.MemberResDTO;
import com.example.Spring_Boot.domain.member.entity.Member;
import com.example.Spring_Boot.domain.member.exception.MemberException;
import com.example.Spring_Boot.domain.member.exception.code.MemberErrorCode;
import com.example.Spring_Boot.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public MemberResDTO.MyPageResponse getMyPage(String authorization) {
        Member member = memberRepository.findById(extractMemberId(authorization))
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        return MemberConverter.toMyPageResponse(member);
    }

    @Transactional
    public MemberResDTO.CreateMemberResponse createMember(MemberReqDTO.CreateMemberRequest request) {
        if (memberRepository.existsByEmail(request.email())) {
            throw new MemberException(MemberErrorCode.DUPLICATE_EMAIL);
        }

        String encodedPassword = passwordEncoder.encode(request.password());
        Member member = MemberConverter.toMember(request, encodedPassword);
        Member savedMember = memberRepository.save(member);

        return MemberConverter.toCreateMemberResponse(savedMember, request.categoryIds());
    }

    private Long extractMemberId(String authorization) {
        if (authorization == null || authorization.isBlank()) {
            throw new MemberException(MemberErrorCode.INVALID_AUTHORIZATION);
        }

        try {
            return Long.parseLong(authorization.replace("Bearer", "").trim());
        } catch (NumberFormatException e) {
            throw new MemberException(MemberErrorCode.INVALID_AUTHORIZATION);
        }
    }
}
