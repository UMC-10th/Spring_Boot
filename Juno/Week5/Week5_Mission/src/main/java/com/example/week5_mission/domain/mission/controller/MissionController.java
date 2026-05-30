package com.example.week5_mission.domain.mission.controller;

import com.example.week5_mission.domain.mission.dto.MissionReqDTO;
import com.example.week5_mission.domain.mission.dto.MissionResDTO;
import com.example.week5_mission.domain.mission.service.MissionService;
import com.example.week5_mission.global.apiPayload.ApiResponse;
import com.example.week5_mission.global.apiPayload.code.BaseSuccessCode;
import com.example.week5_mission.global.apiPayload.code.GeneralSuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/missions")
public class MissionController {

    private final MissionService missionService;

    // 미션 조회
    @GetMapping("")
    public ApiResponse<MissionResDTO.MissionList> missions(
            @RequestHeader("Authorization") String auth,
            @RequestParam String queryParameter
    ){
        BaseSuccessCode code = GeneralSuccessCode.OK;
        return ApiResponse.onSuccess(code, missionService.missions(auth, queryParameter));
    }
}
