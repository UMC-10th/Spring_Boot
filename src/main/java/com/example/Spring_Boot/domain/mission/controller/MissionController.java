package com.example.Spring_Boot.domain.mission.controller;

import com.example.Spring_Boot.domain.mission.dto.MissionReqDTO;
import com.example.Spring_Boot.domain.mission.dto.MissionResDTO;
import com.example.Spring_Boot.domain.mission.service.MissionService;
import com.example.Spring_Boot.global.apiPayload.ApiResponse;
import com.example.Spring_Boot.global.apiPayload.code.GeneralSuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/missions")
public class MissionController {

    private final MissionService missionService;

    // 화면 2: 내 미션 목록 (진행중/완료, 페이징)
    @GetMapping("/my")
    public ApiResponse<Page<MissionResDTO.MissionItemDTO>> getMyMissions(
            @RequestParam Long userId,
            @RequestParam(defaultValue = "IN_PROGRESS") String status,
            @RequestParam(defaultValue = "0") int page
    ) {
        return ApiResponse.onSuccess(GeneralSuccessCode.OK,
                missionService.getMyMissions(userId, status, page));
    }

    // 화면 4: 지역별 도전 가능 미션 (페이징)
    @GetMapping
    public ApiResponse<Page<MissionResDTO.MissionItemDTO>> getAvailableMissions(
            @RequestParam String address,
            @RequestParam Long userId,
            @RequestParam(defaultValue = "0") int page
    ) {
        return ApiResponse.onSuccess(GeneralSuccessCode.OK,
                missionService.getAvailableMissions(address, userId, page));
    }

    // 미션 상태 변경
    @PatchMapping("/{missionId}/status")
    public ApiResponse<Void> updateMissionStatus(
            @PathVariable Long missionId,
            @RequestBody MissionReqDTO.UpdateStatusDTO request
    ) {
        return ApiResponse.onSuccess(GeneralSuccessCode.OK);
    }
}