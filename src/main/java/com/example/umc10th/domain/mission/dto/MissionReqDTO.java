package com.example.umc10th.domain.mission.dto;

import lombok.Builder;

public class MissionReqDTO {

    // 미션 성공 누르기
    @Builder
    public record CompleteMission(
            Boolean completed
    ) {}
}
