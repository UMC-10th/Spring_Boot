package com.example.umc10th.domain.mission.service;

import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.member.exception.code.MemberErrorCode;
import com.example.umc10th.domain.member.repository.MemberRepository;
import com.example.umc10th.domain.mission.converter.MissionConverter;
import com.example.umc10th.domain.mission.dto.MissionReqDTO;
import com.example.umc10th.domain.mission.dto.MissionResDTO;
import com.example.umc10th.domain.mission.entity.mapping.MemberMission;
import com.example.umc10th.domain.mission.exception.code.MissionErrorCode;
import com.example.umc10th.domain.mission.repository.MemberMissionRepository;
import com.example.umc10th.global.apiPayload.exception.ProjectException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MissionService {

	private static final Long CURRENT_MEMBER_ID = 1L;

	private final MemberMissionRepository memberMissionRepository;
	private final MemberRepository memberRepository;

	// 내 미션 목록 조회 (진행중/완료 + 페이징)
	public MissionResDTO.MissionList getMissionList(MissionReqDTO.MissionListRequest request) {
		Boolean isComplete = null;

		if (request.status() != null) {
			if ("COMPLETED".equalsIgnoreCase(request.status())) {
				isComplete = true;
			} else if ("IN_PROGRESS".equalsIgnoreCase(request.status())) {
				isComplete = false;
			}
		}

		Pageable pageable = PageRequest.of(
				request.page() == null ? 0 : request.page(),
				request.size() == null ? 10 : request.size()
		);

		Page<MemberMission> page = memberMissionRepository.findByMemberIdAndIsComplete(
				CURRENT_MEMBER_ID,
				isComplete,
				pageable
		);

		return MissionConverter.toMissionListResponse(page);
	}

	// 미션 완료
	@Transactional
	public MissionResDTO.CompleteMission completeMission(Long memberMissionId, MissionReqDTO.CompleteMission request) {
		if (!Boolean.TRUE.equals(request.completed())) {
			throw new ProjectException(MissionErrorCode.MISSION_INVALID_REQUEST);
		}

		MemberMission memberMission = memberMissionRepository.findById(memberMissionId)
				.orElseThrow(() -> new ProjectException(MissionErrorCode.MISSION_NOT_FOUND));

		if (Boolean.TRUE.equals(memberMission.getIsComplete())) {
			throw new ProjectException(MissionErrorCode.MISSION_ALREADY_COMPLETED);
		}

		Member member = memberRepository.findById(CURRENT_MEMBER_ID)
				.orElseThrow(() -> new ProjectException(MemberErrorCode.MEMBER_NOT_FOUND));

		memberMission.complete();
		member.addPoint(memberMission.getMission().getPoint());

		return MissionConverter.toCompleteMissionResponse(memberMission, member);
	}
}