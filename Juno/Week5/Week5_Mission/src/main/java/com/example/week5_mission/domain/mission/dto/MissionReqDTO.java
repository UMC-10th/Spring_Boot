package com.example.week5_mission.domain.mission.dto;

import lombok.Getter;

public class MissionReqDTO {

    @Getter
    public static class Missions {
        private String keyword;
        private int cursor;
        private int size;
    }
}
