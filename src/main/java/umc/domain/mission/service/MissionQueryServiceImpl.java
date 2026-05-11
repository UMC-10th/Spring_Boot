package umc.domain.mission.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.domain.mission.repository.UserMissionRepository;
import umc.domain.user.entity.mapping.MissionStatus;
import umc.domain.user.entity.mapping.UserMission;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MissionQueryServiceImpl implements MissionQueryService { // 인터페이스는 별도 생성 필요

    private final UserMissionRepository userMissionRepository;

    @Override
    public Page<UserMission> getMyMissions(Long userId, MissionStatus status, Integer page) {
        return userMissionRepository.findMyMissions(userId, status, PageRequest.of(page, 10));
    }
}