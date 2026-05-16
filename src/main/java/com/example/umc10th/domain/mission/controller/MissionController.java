package com.example.umc10th.domain.mission.controller;

import com.example.umc10th.domain.mission.dto.MissionReqDTO;
import com.example.umc10th.domain.mission.dto.MissionResDTO;
import com.example.umc10th.domain.mission.exception.code.MissionSuccessCode;
import com.example.umc10th.domain.mission.service.MissionService;
import com.example.umc10th.global.apiPayload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/missions")
public class MissionController {

    private final MissionService missionService;

    @PostMapping
    public ApiResponse<MissionResDTO.CreateMissionResultDTO> createMission(
            @RequestBody MissionReqDTO.CreateMissionDTO request
    ) {
        return ApiResponse.onSuccess(
                MissionSuccessCode.MISSION_CREATED,
                missionService.createMission(request)
        );
    }

    @GetMapping("/{missionId}")
    public ApiResponse<MissionResDTO.MissionInfoDTO> getMission(
            @PathVariable Long missionId
    ) {
        return ApiResponse.onSuccess(
                MissionSuccessCode.MISSION_FOUND,
                missionService.getMission(missionId)
        );
    }

    @GetMapping("/home")
    public ApiResponse<MissionResDTO.HomeMissionListDTO> getHomeMissions(
            @RequestParam Long locationId,
            @RequestParam(defaultValue = "0") Integer page
    ) {
        return ApiResponse.onSuccess(
                MissionSuccessCode.HOME_MISSION_FOUND,
                missionService.getHomeMissions(locationId, page)
        );
    }
}