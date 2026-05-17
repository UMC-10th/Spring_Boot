package com.example.umc10th.domain.mission.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class MissionResDTO {

	// 미션 목록
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

	// 페이지 정보
	public record PageInfo(
			Integer page,
			Integer size,
			Long totalElements,
			Integer totalPages
	) {
	}

	// 미션 목록 응답
	public record MissionList(
			List<MissionSummary> missions,
			PageInfo pageInfo
	) {
	}

	// 미션 성공 응답
	public record CompleteMission(
			Long memberMissionId,
			String status,
			Integer earnedPoint,
			Integer currentPoint,
			LocalDateTime completedAt
	) {
	}

	// 가게 내 미션 - 단건
	public record GetMission(
			Long missionId,
			Integer point,
			String condition
	){}

	// 가게 내 미션 목록 응답
	public record StoreMissionList(
			List<GetMission> missions,
			PageInfo pageInfo
	){}
}