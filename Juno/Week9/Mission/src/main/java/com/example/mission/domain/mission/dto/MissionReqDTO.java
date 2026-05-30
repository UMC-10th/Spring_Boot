package com.example.mission.domain.mission.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public class MissionReqDTO {

    // 가게 미션 생성
    public record CreateMission(
            @NotNull(message = "마감기한은 필수입니다.")
            LocalDate deadline,
            @NotNull(message = "미션 성공 포인트는 필수입니다.")
            @Positive(message = "포인트는 0보다 커야 합니다.")
            Integer point,
            @NotBlank(message = "조건은 빈 칸일 수 없습니다.")
            String conditional
    ){}
}
