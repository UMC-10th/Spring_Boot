package com.example.Spring_Boot.domain.mission.controller;

import com.example.Spring_Boot.domain.mission.dto.MissionResDTO;
import com.example.Spring_Boot.domain.mission.enums.Status;
import com.example.Spring_Boot.domain.mission.exception.code.MissionSuccessCode;
import com.example.Spring_Boot.domain.mission.service.MissionService;
import com.example.Spring_Boot.global.apiPayload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
}
