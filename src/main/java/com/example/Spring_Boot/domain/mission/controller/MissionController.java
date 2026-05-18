package com.example.Spring_Boot.domain.mission.controller;

import com.example.Spring_Boot.domain.mission.dto.MissionReqDTO;
import com.example.Spring_Boot.domain.mission.dto.MissionResDTO;
import com.example.Spring_Boot.domain.mission.enums.Status;
import com.example.Spring_Boot.domain.mission.exception.code.MissionSuccessCode;
import com.example.Spring_Boot.domain.mission.service.MissionService;
import com.example.Spring_Boot.global.apiPayload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/missions")
public class MissionController {

    private final MissionService missionService;

    // 미션 목록 조회
    @GetMapping
    public ApiResponse<MissionResDTO.MissionListResponse> getUserMissions(
            @RequestParam Status status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestHeader("Authorization") String authorization
    ) {
        MissionResDTO.MissionListResponse response = missionService.getUserMissions(status, page, size, authorization);

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

        return ApiResponse.onSuccess(
                MissionSuccessCode.USER_MISSION_SUCCESS_OK,
                response
        );
    }
}
