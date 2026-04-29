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
            @RequestBody MissionReqDTO.Missions dto
    ){
        BaseSuccessCode code = GeneralSuccessCode.OK;
        return ApiResponse.onSuccess(code, missionService.missions(auth, dto));
    }

    // 미션 성공
    // Path Variable
    @PostMapping("/{missionId}")
    public ApiResponse<MissionResDTO.Mission> missionSuccess(
            @PathVariable String missionId,
            @RequestHeader("Authorization") String auth
    ){
        BaseSuccessCode code = GeneralSuccessCode.OK;
        return ApiResponse.onSuccess(code, missionService.missionSuccess(missionId, auth))
    }
}
