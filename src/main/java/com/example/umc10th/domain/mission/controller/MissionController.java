package com.example.umc10th.domain.mission.controller;

import com.example.umc10th.domain.mission.dto.MissionReqDTO;
import com.example.umc10th.domain.mission.dto.MissionResDTO;
import com.example.umc10th.domain.mission.service.MissionService;
import com.example.umc10th.global.apiPayload.ApiResponse;
import com.example.umc10th.global.apiPayload.code.GeneralSuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member-missions")
public class MissionController {

	private final MissionService missionService;

	// 내 미션 목록 조회
	@GetMapping("")
	public ApiResponse<MissionResDTO.MissionList> getMissionList(
			@ModelAttribute MissionReqDTO.MissionListRequest request
	) {
		return ApiResponse.onSuccess(
				GeneralSuccessCode.OK,
				missionService.getMissionList(request)
		);
	}

	// 미션 완료
	@PatchMapping("/{memberMissionId}/complete")
	public ApiResponse<MissionResDTO.CompleteMission> completeMission(
			@PathVariable Long memberMissionId,
			@RequestBody MissionReqDTO.CompleteMission request
	) {
		return ApiResponse.onSuccess(
				GeneralSuccessCode.OK,
				missionService.completeMission(memberMissionId, request)
		);
	}
}