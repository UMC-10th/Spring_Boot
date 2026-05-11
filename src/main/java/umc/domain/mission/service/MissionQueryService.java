package umc.domain.mission.service;

import org.springframework.data.domain.Page;
import umc.domain.user.entity.mapping.MissionStatus;
import umc.domain.user.entity.mapping.UserMission;

public interface MissionQueryService {
    Page<UserMission> getMyMissions(Long userId, MissionStatus status, Integer page);
}