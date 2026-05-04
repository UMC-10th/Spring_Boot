package com.example.Spring_Boot.domain.mission.dto;

import com.example.Spring_Boot.domain.mission.enums.Status;
import lombok.Builder;

public class MissionReqDTO {

    @Builder
    public record MissionSuccessRequest(
            Status status
    ) {
    }
}
