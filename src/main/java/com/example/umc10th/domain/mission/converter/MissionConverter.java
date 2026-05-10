package com.example.umc10th.domain.mission.converter;

import com.example.umc10th.domain.mission.dto.MissionReqDTO;
import com.example.umc10th.domain.mission.dto.MissionResDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class MissionConverter {

	private MissionConverter() {
	}

	public static MissionResDTO.MissionList toMissionListResponse(MissionReqDTO.MissionListRequest request) {
		MissionResDTO.MissionSummary missionSummary = new MissionResDTO.MissionSummary(
				101L,
				10L,
				3L,
				"현안국밥",
				"10,000원 이상 주문 시",
				500,
				request.status() == null ? "IN_PROGRESS" : request.status(),
				LocalDate.of(2026, 3, 31),
				null
		);

		MissionResDTO.PageInfo pageInfo = new MissionResDTO.PageInfo(
				request.page() == null ? 0 : request.page(),
				request.size() == null ? 10 : request.size(),
				24L,
				3
		);

		return new MissionResDTO.MissionList(List.of(missionSummary), pageInfo);
	}

	public static MissionResDTO.CompleteMission toCompleteMissionResponse(
			Long memberMissionId,
			MissionReqDTO.CompleteMission request
	) {
		return new MissionResDTO.CompleteMission(
				memberMissionId,
				Boolean.TRUE.equals(request.completed()) ? "COMPLETED" : "IN_PROGRESS",
				500,
				3000,
				LocalDateTime.of(2026, 3, 25, 14, 32, 10)
		);
	}
}
