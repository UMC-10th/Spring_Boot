package com.example.umc10th.domain.mission.dto;

import lombok.Builder;

import java.time.LocalDateTime;

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
}