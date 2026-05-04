package com.example.Spring_Boot.domain.mission.service;

import com.example.Spring_Boot.domain.mission.dto.MissionReqDTO;
import com.example.Spring_Boot.domain.mission.dto.MissionResDTO;
import com.example.Spring_Boot.domain.mission.enums.Status;

public class MissionService {
    public MissionResDTO.MissionListResponse getUserMissions(Status status, String authorization) {
        return null;
    }

    public MissionResDTO.MissionSuccessResponse completeUserMission(Long userMissionId, String authorization, MissionReqDTO.MissionSuccessRequest request) {
        return null;
    }
}
