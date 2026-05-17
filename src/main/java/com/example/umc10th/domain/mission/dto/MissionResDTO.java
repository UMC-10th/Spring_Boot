package com.example.umc10th.domain.mission.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class MissionResDTO {

	public record MissionSummary(
			Long memberMissionId,
			Long missionId,
			Long storeId,
			String storeName,
			String condition,
			Integer rewardPoint,
			String status,
			LocalDate deadline,
			LocalDateTime completedAt
	) {
	}

	public record PageInfo(
			Integer page,
			Integer size,
			Long totalElements,
			Integer totalPages
	) {
	}

	public record MissionList(
			List<MissionSummary> missions,
			PageInfo pageInfo
	) {
	}

	public record CompleteMission(
			Long memberMissionId,
			String status,
			Integer earnedPoint,
			Integer currentPoint,
			LocalDateTime completedAt
	) {
	}
}