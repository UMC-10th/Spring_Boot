package com.example.umc10th.domain.member.service;

import com.example.umc10th.domain.member.converter.MemberConverter;
import com.example.umc10th.domain.member.dto.MemberReqDTO;
import com.example.umc10th.domain.member.dto.MemberResDTO;
import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.member.exception.MemberException;
import com.example.umc10th.domain.member.exception.code.MemberErrorCode;
import com.example.umc10th.domain.member.repository.MemberRepository;
import com.example.umc10th.global.apiPayload.code.GeneralErrorCode;
import com.example.umc10th.global.security.entity.AuthMember;
import com.example.umc10th.global.security.util.JwtUtil;
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

    // 구버전: Request Body로 ID를 직접 받아 조회
    public MemberResDTO.GetInfo getInfo(MemberReqDTO.GetInfo dto) {
        // TODO: 구현 예정
        return null;
    }

    // v2: SecurityContextHolder에서 AuthMember를 받아 컨버터에 직접 전달 (추가 DB 조회 불필요)
    public MemberResDTO.GetInfo getInfo(AuthMember authMember) {
        return MemberConverter.toGetInfo(authMember.getMember());
    }

    public MemberResDTO.GetInfo getMyPage(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException(GeneralErrorCode.NOT_FOUND.getMessage()));
        return MemberConverter.toGetInfo(member);
    }

    // 회원가입
    @Transactional
    public MemberResDTO.SignUp signUp(MemberReqDTO.SignUp dto) {
        String encodedPassword = passwordEncoder.encode(dto.password());
        Member member = MemberConverter.toMember(dto, encodedPassword);
        Member savedMember = memberRepository.save(member);
        return MemberConverter.toSignUp(savedMember);
    }

    public MemberResDTO.Login login(MemberReqDTO.Login dto) {

        Member member = memberRepository.findByEmail(dto.email())
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        if (!passwordEncoder.matches(dto.password(), member.getPassword())) {
            throw new MemberException(MemberErrorCode.INVALID_PASSWORD);
        }

        String accessToken = jwtUtil.createAccessToken(new AuthMember(member));
        return MemberConverter.toLogin(accessToken);
    }
}
