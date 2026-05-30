package com.example.week6_mission.domain.mission.dto;

import lombok.Getter;

public class MissionResDTO {

    @Getter
    public static class MissionList {
        Mission[] missionList;
    }

    @Getter
    public static class Mission {
        int point;
    }
}
