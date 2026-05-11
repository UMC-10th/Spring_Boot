package com.example.umc10thtest.domain.mission.dto;

import lombok.Getter;

public class MissionReqDTO {

    @Getter
    public static class MissionListReq {
        private Long locationId;
        private Long memberId;
    }
}
