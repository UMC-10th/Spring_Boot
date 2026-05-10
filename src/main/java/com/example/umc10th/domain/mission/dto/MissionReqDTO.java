package com.example.umc10th.domain.mission.dto;

public class MissionReqDTO {

	public record MissionListRequest(
			String status,
			Integer page,
			Integer size
	) {
	}

	public record CompleteMission(
			Boolean completed
	) {
	}
}
