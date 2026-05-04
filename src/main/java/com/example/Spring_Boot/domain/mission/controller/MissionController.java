package com.example.Spring_Boot.domain.mission.controller;

import com.example.Spring_Boot.domain.mission.dto.MissionReqDTO;
import com.example.Spring_Boot.domain.mission.dto.MissionResDTO;
import com.example.Spring_Boot.global.apiPayload.ApiResponse;
import com.example.Spring_Boot.global.apiPayload.code.GeneralSuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/missions")
public class MissionController {

    // 미션 목록 조회 (진행중/완료)
    @GetMapping
    public ApiResponse<MissionResDTO.MissionListDTO> getMissionList(
            @RequestParam String status
    ) {
        return ApiResponse.onSuccess(GeneralSuccessCode.OK, null);
    }

    // 미션 성공 처리
    @PatchMapping("/{missionId}/status")
    public ApiResponse<Void> updateMissionStatus(
            @PathVariable Long missionId,
            @RequestBody MissionReqDTO.UpdateStatusDTO request
    ) {
        return ApiResponse.onSuccess(GeneralSuccessCode.OK);
    }
}