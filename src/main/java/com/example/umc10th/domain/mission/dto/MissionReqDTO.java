package com.example.umc10th.domain.mission.dto;

public class MissionReqDTO {

    public record CreateMissionDTO(
            Long storeId,
            String title,
            String description
    ) {
    }
}