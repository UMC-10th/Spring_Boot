package com.example.umc10th.domain.mission.service;

import com.example.umc10th.domain.member.exception.MemberException;
import com.example.umc10th.domain.member.exception.code.MemberErrorCode;
import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.member.repository.MemberRepository;
import com.example.umc10th.domain.mission.converter.MissionConverter;
import com.example.umc10th.domain.mission.dto.MissionResDTO;
import com.example.umc10th.domain.mission.entity.Mission;
import com.example.umc10th.domain.mission.entity.MissionParticipation;
import com.example.umc10th.domain.mission.entity.enums.MissionStatus;
import com.example.umc10th.domain.mission.exception.MissionException;
import com.example.umc10th.domain.mission.exception.code.MissionErrorCode;
import com.example.umc10th.domain.mission.repository.MissionParticipationRepository;
import com.example.umc10th.domain.mission.repository.MissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MissionService {

    private final MissionRepository missionRepository;
    private final MemberRepository memberRepository;
    private  final MissionParticipationRepository missionParticipationRepository;

    public MissionResDTO.MissionList getMissions(
            Long memberId, Long areaId, Integer page, Integer size) {

        if (memberRepository.findByMemberIdAndDeletedAtIsNull(memberId).isEmpty()) {
            throw new MemberException(MemberErrorCode.MEMBER_NOT_FOUND);
        }

        Pageable pageable = PageRequest.of(page, size);

        Page<Mission> result = missionRepository
                .findChallengeableByAreaId(memberId, areaId, LocalDateTime.now(), pageable);

        return MissionConverter.toMissionList(result);
    }
    public MissionResDTO.Progress getProgress(Long memberId) {
        if (memberRepository.findByMemberIdAndDeletedAtIsNull(memberId).isEmpty()) {
            throw new MemberException(MemberErrorCode.MEMBER_NOT_FOUND);
        }

        long total = missionParticipationRepository.countByMemberId(memberId);
        long completed = missionParticipationRepository
                .countByMemberIdAndMissionStatus(memberId, MissionStatus.COMPLETED);

        return MissionResDTO.Progress.builder()
                .totalCount((int) total)
                .completedCount((int) completed)
                .build();
    }
    @Transactional
    public MissionResDTO.ParticipateResult participate(Long memberId, Long missionId) {
        Member member = memberRepository.findByMemberIdAndDeletedAtIsNull(memberId)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        Mission mission = missionRepository.findById(missionId)
                .orElseThrow(() -> new MissionException(MissionErrorCode.MISSION_NOT_FOUND));

        if (missionParticipationRepository.existsByMemberIdAndMissionId(memberId, missionId)) {
            throw new MissionException(MissionErrorCode.ALREADY_PARTICIPATING);
        }

        MissionParticipation participation = MissionParticipation.builder()
                .member(member)
                .mission(mission)
                .build();

        MissionParticipation saved = missionParticipationRepository.save(participation);

        return MissionResDTO.ParticipateResult.builder()
                .participationId(saved.getId())
                .missionStatus(saved.getMissionStatus().name())
                .createdAt(saved.getCreatedAt())
                .build();
    }

    @Transactional
    public MissionResDTO.CompleteResult complete(Long memberId, Long missionId) {
        MissionParticipation participation = missionParticipationRepository
                .findByMemberIdAndMissionId(memberId, missionId)
                .orElseThrow(() -> new MissionException(MissionErrorCode.NOT_PARTICIPATING));

        if (participation.getMissionStatus() == MissionStatus.COMPLETED) {
            return MissionResDTO.CompleteResult.builder()
                    .participationId(participation.getId())
                    .missionStatus(participation.getMissionStatus().name())
                    .completedAt(participation.getCompletedAt())
                    .pointReward(participation.getMission().getPointReward())
                    .build();
        }

        participation.complete();  // 엔티티의 의미 메서드 호출 → 변경 감지로 자동 UPDATE

        return MissionResDTO.CompleteResult.builder()
                .participationId(participation.getId())
                .missionStatus(participation.getMissionStatus().name())
                .completedAt(participation.getCompletedAt())
                .pointReward(participation.getMission().getPointReward())
                .build();
    }
}