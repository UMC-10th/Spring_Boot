package com.example.Spring_Boot.domain.mission.dto;

import com.example.Spring_Boot.domain.mission.enums.Status;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

public class MissionReqDTO {

    @Builder
    public record InProgressMissionRequest(
            @NotNull(message = "유저 ID는 필수입니다.")
            @Positive(message = "유저 ID는 양수여야 합니다.")
            Long userId
    ) {
    }

    @Builder
    public record MissionSuccessRequest(
            @NotNull(message = "미션 상태는 필수입니다.")
            Status status
    ) {
    }
}
