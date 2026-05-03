package com.example.Spring_Boot.domain.mission.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

public class MissionReqDTO {

    @Getter
    @NoArgsConstructor
    public static class UpdateStatusDTO {
        private String status;
    }
}