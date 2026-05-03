package com.example.umc10th.domain.mission.dto;

import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class MissionResDTO {

    // 미션 목록 - 단건 정보
    @Builder
    public record MissionInfo(
            Long memberMissionId,
            Long missionId,
            Long storeId,
            String storeName,
            String condition,
            Integer rewardPoint,
            String status,         // "IN_PROGRESS", "COMPLETED"
            LocalDate deadline,
            LocalDateTime completedAt
    ) {}

    // 페이지 정보
    @Builder
    public record PageInfo(
            Integer page,
            Integer size,
            Long totalElements,
            Integer totalPages
    ) {}

    // 미션 목록 응답
    @Builder
    public record MissionList(
            List<MissionInfo> missions,
            PageInfo pageInfo
    ) {}

    // 미션 성공 응답
    @Builder
    public record CompleteMission(
            Long memberMissionId,
            String status,
            Integer earnedPoint,
            Integer currentPoint,
            LocalDateTime completedAt
    ) {}
}
