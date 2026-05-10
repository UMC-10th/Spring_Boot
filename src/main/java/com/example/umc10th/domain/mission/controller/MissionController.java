package com.example.umc10th.domain.mission.controller;

import com.example.umc10th.domain.mission.dto.MissionReqDTO;
import com.example.umc10th.domain.mission.dto.MssionResDTO;
import com.example.umc10th.domain.mission.exception.code.MissionSuccessCode;
import com.example.umc10th.domain.mission.service.MissionService;
import com.example.umc10th.global.apiPayload.ApiResponse;
import com.example.umc10th.global.apiPayload.code.BaseSuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class MissionController {

    private final MissionService missionService;

    // 홈 화면
    // TODO: Security 적용 후 매개변수 변경
    @GetMapping("/home")
    public ResponseEntity<ApiResponse<MssionResDTO.GetHome>> getHome(
            @RequestParam Long memberId,
            @RequestParam Long locationId
    ) {
        MissionReqDTO.GetHome req = new MissionReqDTO.GetHome(memberId, locationId);
        BaseSuccessCode code = MissionSuccessCode.HOME_OK;
        return ApiResponse.of(code, missionService.getHome(req));
    }

    // 내 미션 목록 조회
    // TODO: Security 적용 후 매개변수 변경
    @GetMapping("/my/missions")
    public ResponseEntity<ApiResponse<MssionResDTO.GetMyMissions>> getMyMissions(
            @RequestParam Long memberId,
            @RequestParam Boolean isComplete
    ) {
        MissionReqDTO.GetMyMissions req = new MissionReqDTO.GetMyMissions(memberId, isComplete);
        BaseSuccessCode code = MissionSuccessCode.MY_MISSIONS_OK;
        return ApiResponse.of(code, missionService.getMyMissions(req));
    }

    //  미션 완료 처리
    // PATCH /api/v1/my/missions/{missionId}/complete
    @PatchMapping("/my/missions/{missionId}/complete")
    public ResponseEntity<ApiResponse<MssionResDTO.CompleteMission>> completeMission(
            @PathVariable Long missionId
    ) {
        BaseSuccessCode code = MissionSuccessCode.MISSION_COMPLETE_OK;
        return ApiResponse.of(code, missionService.completeMission(missionId));
    }
}

