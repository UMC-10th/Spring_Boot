package com.example.umc10th.domain.mission.converter;

import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.mission.dto.MissionReqDTO;
import com.example.umc10th.domain.mission.dto.MissionResDTO;
import com.example.umc10th.domain.mission.entity.Mission;
import com.example.umc10th.domain.mission.entity.Store;
import com.example.umc10th.domain.mission.entity.mapping.MemberMission;
import org.springframework.data.domain.Page;

import java.util.List;

public class MissionConverter {

	private MissionConverter() {
	}

	// 가게 미션 생성
	public static Mission toMission(Store store, MissionReqDTO.CreateMission dto) {
		return Mission.builder()
				.store(store)
				.condition(dto.condition())
				.point(dto.point())
				.deadline(dto.deadline())
				.build();
	}

	// 내 미션 요약
	public static MissionResDTO.MissionSummary toMissionSummary(MemberMission memberMission) {
		Mission mission = memberMission.getMission();

		return new MissionResDTO.MissionSummary(
				memberMission.getId(),
				mission.getId(),
				mission.getStore().getId(),
				mission.getStore().getName(),
				mission.getCondition(),
				mission.getPoint(),
				Boolean.TRUE.equals(memberMission.getIsComplete()) ? "COMPLETED" : "IN_PROGRESS",
				mission.getDeadline(),
				Boolean.TRUE.equals(memberMission.getIsComplete()) ? memberMission.getCompletedAt() : null
		);
	}

	// 내 미션 목록 페이징 응답
	public static MissionResDTO.MissionList toMissionListResponse(Page<MemberMission> page) {
		List<MissionResDTO.MissionSummary> missions = page.getContent().stream()
				.map(MissionConverter::toMissionSummary)
				.toList();

		MissionResDTO.PageInfo pageInfo = new MissionResDTO.PageInfo(
				page.getNumber(),
				page.getSize(),
				page.getTotalElements(),
				page.getTotalPages()
		);

		return new MissionResDTO.MissionList(missions, pageInfo);
	}

	// 미션 완료 응답
	public static MissionResDTO.CompleteMission toCompleteMissionResponse(
			MemberMission memberMission,
			Member member
	) {
		return new MissionResDTO.CompleteMission(
				memberMission.getId(),
				"COMPLETED",
				memberMission.getMission().getPoint(),
				member.getPoint(),
				memberMission.getCompletedAt()
		);
	}

	// 가게 내 미션 목록 조회
	public static MissionResDTO.GetMission toGetMission(Mission mission) {
		return new MissionResDTO.GetMission(
				mission.getId(),
				mission.getPoint(),
				mission.getCondition()
		);
	}

	// 가게 내 미션 목록 페이징 응답
	public static MissionResDTO.StoreMissionList toStoreMissionListResponse(Page<Mission> page) {
		List<MissionResDTO.GetMission> missions = page.getContent().stream()
				.map(MissionConverter::toGetMission)
				.toList();

		MissionResDTO.PageInfo pageInfo = new MissionResDTO.PageInfo(
				page.getNumber(),
				page.getSize(),
				page.getTotalElements(),
				page.getTotalPages()
		);

		return new MissionResDTO.StoreMissionList(missions, pageInfo);
	}
}