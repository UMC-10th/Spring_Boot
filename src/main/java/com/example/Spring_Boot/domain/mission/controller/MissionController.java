package com.example.Spring_Boot.domain.mission.controller;

import com.example.Spring_Boot.domain.mission.dto.MissionReqDTO;
import com.example.Spring_Boot.domain.mission.dto.MissionResDTO;
import com.example.Spring_Boot.domain.mission.enums.Status;
import com.example.Spring_Boot.domain.mission.exception.code.MissionSuccessCode;
import com.example.Spring_Boot.domain.mission.service.MissionService;
import com.example.Spring_Boot.global.apiPayload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/missions")
public class MissionController {

    private final MissionService missionService;

    // 미션 목록 조회
    @GetMapping
    public ApiResponse<MissionResDTO.MissionListResponse> getUserMissions(
            @RequestParam Status status,
            @RequestHeader("Authorization") String authorization
    ) {
        MissionResDTO.MissionListResponse response = missionService.getUserMissions(status, authorization);

        return ApiResponse.onSuccess(
                MissionSuccessCode.USER_MISSION_LIST_OK,
                response
        );
    }

    // 미션 완료
    @PatchMapping("/{userMissionId}")
    public ApiResponse<MissionResDTO.MissionSuccessResponse> completeUserMission(
            @PathVariable("userMissionId") Long userMissionId,
            @RequestHeader("Authorization") String authorization,
            @RequestBody MissionReqDTO.MissionSuccessRequest request
    ) {
        MissionResDTO.MissionSuccessResponse response =
                missionService.completeUserMission(
                        userMissionId,
                        authorization,
                        request
                );
    }
}
