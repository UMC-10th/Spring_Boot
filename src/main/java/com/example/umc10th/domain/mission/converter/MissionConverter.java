package com.example.umc10th.domain.mission.converter;

import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.mission.dto.MissionResDTO;
import com.example.umc10th.domain.mission.entity.Mission;
import com.example.umc10th.domain.mission.entity.mapping.MemberMission;
import org.springframework.data.domain.Page;

import java.util.List;

public class MissionConverter {

	private MissionConverter() {
	}

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
}