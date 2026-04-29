package com.example.week5_mission.domain.mission.dto;

import com.example.week5_mission.domain.mission.entity.Mission;
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
