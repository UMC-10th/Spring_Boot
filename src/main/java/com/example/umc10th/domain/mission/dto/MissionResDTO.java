package com.example.umc10th.domain.mission.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

public class MissionResDTO {

    @Builder
    public record CreateMissionResultDTO(
            Long missionId,
            String title,
            String description,
            LocalDateTime createdAt
    ) {
    }

    @Builder
    public record MissionInfoDTO(
            Long missionId,
            String title,
            String description
    ) {
    }

    @Builder
    public record HomeMissionDTO(
            Long missionId,
            String storeName,
            String missionTitle,
            String missionDescription
    ) {
    }

    @Builder
    public record HomeMissionListDTO(
            List<HomeMissionDTO> missionList,
            Integer listSize,
            Integer totalPage,
            Long totalElements,
            Boolean isFirst,
            Boolean isLast
    ) {
    }
}