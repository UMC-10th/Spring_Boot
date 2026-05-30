package com.example.week6_mission.domain.mission.service;

import com.example.week6_mission.domain.mission.dto.MissionResDTO;
import com.example.week6_mission.domain.mission.entity.Mission;
import com.example.week6_mission.domain.mission.repository.MissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MissionService {

	private final MissionRepository missionRepository;

	public MissionResDTO.MissionList missions(String auth, String storeId) {
		Long id = Long.parseLong(storeId);
		List<Mission> missions = missionRepository.findAllByStore_Id(id);

		MissionResDTO.Mission[] missionResponses = missions.stream()
				.map(mission -> toMissionResponse(mission.getPoint()))
				.toArray(MissionResDTO.Mission[]::new);

		return toMissionListResponse(missionResponses);
	}

	private MissionResDTO.Mission toMissionResponse(int point) {
		try {
			MissionResDTO.Mission response = new MissionResDTO.Mission();
			Field pointField = MissionResDTO.Mission.class.getDeclaredField("point");
			pointField.setAccessible(true);
			pointField.setInt(response, point);
			return response;
		} catch (ReflectiveOperationException exception) {
			throw new IllegalStateException("Failed to build mission response", exception);
		}
	}

	private MissionResDTO.MissionList toMissionListResponse(MissionResDTO.Mission[] missionResponses) {
		try {
			MissionResDTO.MissionList response = new MissionResDTO.MissionList();
			Field missionListField = MissionResDTO.MissionList.class.getDeclaredField("missionList");
			missionListField.setAccessible(true);
			missionListField.set(response, missionResponses);
			return response;
		} catch (ReflectiveOperationException exception) {
			throw new IllegalStateException("Failed to build mission list response", exception);
		}
	}
}
