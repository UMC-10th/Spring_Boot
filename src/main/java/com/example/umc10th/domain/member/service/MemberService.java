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
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        Member member = memberRepository.findByMemberIdAndDeletedAtIsNull(memberId)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        long reviewCount = reviewRepository.countByMemberMemberId(memberId);

        return MemberConverter.toMyPage(member, reviewCount);
    }

    public MemberResDTO.MissionList getMyMissions(
            Long memberId, String status, Integer page, Integer size) {

        if (memberRepository.findByMemberIdAndDeletedAtIsNull(memberId).isEmpty()) {
            throw new MemberException(MemberErrorCode.MEMBER_NOT_FOUND);
        }

        MissionStatus missionStatus = MissionStatus.valueOf(status);
        Pageable pageable = PageRequest.of(page, size);

        Page<MissionParticipation> result = missionParticipationRepository
                .findByMemberIdAndStatusWithFetch(memberId, missionStatus, pageable);

        return MemberConverter.toMissionList(result);
    }
}