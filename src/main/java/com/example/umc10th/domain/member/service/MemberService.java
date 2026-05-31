package com.example.umc10th.domain.member.service;

import com.example.umc10th.domain.member.converter.MemberConverter;
import com.example.umc10th.domain.member.dto.MemberReqDTO;
import com.example.umc10th.domain.member.dto.MemberResDTO;
import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.member.exception.MemberException;
import com.example.umc10th.domain.member.exception.code.MemberErrorCode;
import com.example.umc10th.domain.member.repository.MemberRepository;
import com.example.umc10th.domain.mission.entity.MissionParticipation;
import com.example.umc10th.domain.mission.entity.enums.MissionStatus;
import com.example.umc10th.domain.mission.repository.MissionParticipationRepository;
import com.example.umc10th.domain.review.repository.ReviewRepository;
import com.example.umc10th.global.apiPayload.dto.OffsetPage;
import com.example.umc10th.global.security.AuthMember;          
import com.example.umc10th.global.security.util.JwtUtil;        
import org.springframework.security.crypto.password.PasswordEncoder;  

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final ReviewRepository reviewRepository;
    private final MissionParticipationRepository missionParticipationRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional
    public MemberResDTO.JoinResult join(MemberReqDTO.Join request) {
        // 이메일 중복 체크 (탈퇴하지 않은 회원 기준)
        if (memberRepository.existsByEmailAndDeletedAtIsNull(request.email())) {
            throw new MemberException(MemberErrorCode.DUPLICATE_EMAIL);
        }
        // 비밀번호 BCrypt 해싱 (솔트 자동 생성 + 해시에 포함)
        String encodedPassword = passwordEncoder.encode(request.password());

        // 컨버터로 엔티티 생성 (해싱된 비밀번호 전달)
        Member member = MemberConverter.toMember(request, encodedPassword);

        // DB 저장
        Member saved = memberRepository.save(member);

        // 응답 DTO 변환 후 반환
        return MemberConverter.toJoinResult(saved);
    }

    public MemberResDTO.LoginResult login(MemberReqDTO.Login request) {
        // 1. 이메일로 회원 조회 (없으면 비밀번호 불일치와 동일 에러로 처리 → 이메일 존재 여부 노출 방지)
        Member member = memberRepository.findByEmailAndDeletedAtIsNull(request.email())
                .orElseThrow(() -> new MemberException(MemberErrorCode.INVALID_PASSWORD));

        // 2. 입력 비밀번호(평문)와 저장된 해시 비교
        if (!passwordEncoder.matches(request.password(), member.getPassword())) {
            throw new MemberException(MemberErrorCode.INVALID_PASSWORD);
        }

        // 3. 인증 통과 → JWT 발급
        String accessToken = jwtUtil.createAccessToken(new AuthMember(member));

        // 4. 응답 DTO
        return MemberResDTO.LoginResult.builder()
                .accessToken(accessToken)
                .tokenType("Bearer")
                .build();
    }

    public MemberResDTO.MyPage getMyPage(Long memberId) {
        Member member = memberRepository.findByIdAndDeletedAtIsNull(memberId)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        long reviewCount = reviewRepository.countByMemberId(memberId);

        return MemberConverter.toMyPage(member, reviewCount);
    }

    public OffsetPage<MemberResDTO.MissionListItem> getMyInProgressMissions(
            Long memberId, Integer page, Integer size) {
        // 1. 회원 존재 확인 (soft delete 회원 제외)
        if (memberRepository.findByIdAndDeletedAtIsNull(memberId).isEmpty()) {
            throw new MemberException(MemberErrorCode.MEMBER_NOT_FOUND);
        }

        // 2. 페이징 조건 구성 — 최신순(생성일 내림차순)
        Pageable pageable = PageRequest.of(page, size,
                Sort.by(Sort.Direction.DESC, "createdAt"));

        // 3. JOIN FETCH로 N+1을 막으면서 페이징 결과 조회
        Page<MissionParticipation> result = missionParticipationRepository
                .findByMemberIdAndStatusWithFetch(memberId, MissionStatus.IN_PROGRESS, pageable);

        // 4. 공통 페이지네이션 응답 DTO로 변환
        return OffsetPage.from(result, MemberConverter::toMissionListItem);
    }
}
