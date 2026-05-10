package com.umc.umc10th.domain.mission.controller;

import com.umc.umc10th.domain.mission.apipayload.code.MissionSuccessCode;
import com.umc.umc10th.domain.mission.dto.response.MissionResponseDto;
import com.umc.umc10th.domain.mission.service.MissionService;
import com.umc.umc10th.domain.store.apipayload.code.StoreSuccessCode;
import com.umc.umc10th.global.apipayload.ApiResponse;
import com.umc.umc10th.global.apipayload.code.BaseSuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/missions")
@RequiredArgsConstructor
public class MissionController {

    private final MissionService missionService;

    @GetMapping("/{locationId}")
    public ApiResponse<MissionResponseDto.GetMissions> getMissions(@PathVariable Long locationId) {
        BaseSuccessCode code = MissionSuccessCode.OK;
        return ApiResponse.onSuccess(code, missionService.getMissions(locationId));
    }

//    @PatchMapping("/{missionId}")
//    public ApiResponse<Void> markAsSuccess(@PathVariable Long missionId) {
//        BaseSuccessCode code = MissionSuccessCode.OK;
//        return ApiResponse.onSuccess(code, missionService.markAsSuccess(missionId));
//    }
}
