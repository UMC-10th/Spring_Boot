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

    public MemberResDTO.JoinResult join(MemberReqDTO.Join request) {
        // TODO: Repository 연결
        return MemberResDTO.JoinResult.builder()
                .memberId(1L)
                .createdAt(LocalDateTime.now())
                .build();
    }

    public MemberResDTO.LoginResult login(MemberReqDTO.Login request) {
        // TODO: Repository 연결
        return MemberResDTO.LoginResult.builder()
                .accessToken("dummy-token")
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
