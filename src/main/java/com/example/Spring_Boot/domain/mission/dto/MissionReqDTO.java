package com.example.Spring_Boot.domain.mission.dto;

import com.example.Spring_Boot.domain.mission.enums.Status;

public class MissionReqDTO {

    public record MissionSuccessRequest(
            Status status
    ) {
    }
}
