package com.example.umc10th.domain.mission.converter;

import com.example.umc10th.domain.mission.dto.MissionResDTO;
import com.example.umc10th.domain.mission.entity.Mission;
import org.springframework.data.domain.Page;

import java.util.List;

public class MissionConverter {

    private MissionConverter() {}

    public static MissionResDTO.ListItem toListItem(Mission mission) {
        return MissionResDTO.ListItem.builder()
                .missionId(mission.getMissionId())
                .title(mission.getMissionName())
                .pointReward(mission.getPointReward())
                .description(mission.getDescription())
                .restaurantName(mission.getRestaurant().getRestaurantName())
                .foodType(mission.getRestaurant().getFoodType())
                .deadline(mission.getDeadline())
                .build();
    }

    public static MissionResDTO.MissionList toMissionList(Page<Mission> page) {
        List<MissionResDTO.ListItem> items = page.getContent().stream()
                .map(MissionConverter::toListItem)
                .toList();

        return MissionResDTO.MissionList.builder()
                .missions(items)
                .totalCount(page.getTotalElements())
                .currentPage(page.getNumber())
                .build();
    }
}