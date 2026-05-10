package com.example.umc10th.domain.mission.service;

import com.example.umc10th.domain.mission.converter.MissionConverter;
import com.example.umc10th.domain.mission.dto.MissionReqDTO;
import com.example.umc10th.domain.mission.dto.MissionResDTO;
import org.springframework.stereotype.Service;

@Service
public class MissionService {

	public MissionResDTO.MissionList getMissionList(
			MissionReqDTO.MissionListRequest request
	) {
		return MissionConverter.toMissionListResponse(request);
	}

	public MissionResDTO.CompleteMission completeMission(
			Long memberMissionId,
			MissionReqDTO.CompleteMission request
	) {
		return MissionConverter.toCompleteMissionResponse(memberMissionId, request);
	}
}
