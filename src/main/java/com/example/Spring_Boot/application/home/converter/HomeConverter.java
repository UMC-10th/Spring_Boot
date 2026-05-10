package com.example.Spring_Boot.application.home.converter;

import com.example.Spring_Boot.application.home.dto.HomeResDTO;
import com.example.Spring_Boot.domain.member.entity.Member;
import com.example.Spring_Boot.domain.mission.entity.Mission;
import com.example.Spring_Boot.domain.mission.entity.mapping.UserMission;
import com.example.Spring_Boot.domain.store.entity.Category;
import com.example.Spring_Boot.domain.store.entity.Region;
import com.example.Spring_Boot.domain.store.entity.Store;
import org.springframework.data.domain.Page;

import java.util.Map;

public class HomeConverter {

    private HomeConverter() {
    }

    public static HomeResDTO.GetHomeResponse toGetHomeResponse(
            Member member,
            Region region,
            Long completedCount,
            Page<UserMission> userMissionPage,
            Map<Long, Category> categoryByStoreId
    ) {
        return HomeResDTO.GetHomeResponse.builder()
                .point(member.getPoint())
                .region(toRegionInfo(region))
                .missionProgress(toMissionProgressInfo(completedCount))
                .missionList(userMissionPage.getContent().stream()
                        .map(userMission -> toMissionInfo(userMission, categoryByStoreId))
                        .toList())
                .pageInfo(toPageInfo(userMissionPage))
                .build();
    }

    private static HomeResDTO.RegionInfo toRegionInfo(Region region) {
        return HomeResDTO.RegionInfo.builder()
                .regionId(region.getRegionId())
                .name(region.getName())
                .build();
    }

    private static HomeResDTO.MissionProgressInfo toMissionProgressInfo(Long completedCount) {
        return HomeResDTO.MissionProgressInfo.builder()
                .completedCount(Math.toIntExact(completedCount))
                .build();
    }

    private static HomeResDTO.MissionInfo toMissionInfo(
            UserMission userMission,
            Map<Long, Category> categoryByStoreId
    ) {
        Mission mission = userMission.getMission();
        Store store = mission.getStore();
        Category category = categoryByStoreId.get(store.getStoreId());

        return HomeResDTO.MissionInfo.builder()
                .userMissionId(userMission.getUserMissionId())
                .status(userMission.getStatus())
                .missionId(mission.getMissionId())
                .missionContent(mission.getContent())
                .compensation(mission.getCompensation())
                .store(toStoreInfo(store))
                .category(toCategoryInfo(category))
                .build();
    }

    private static HomeResDTO.StoreInfo toStoreInfo(Store store) {
        return HomeResDTO.StoreInfo.builder()
                .storeId(store.getStoreId())
                .name(store.getName())
                .build();
    }

    private static HomeResDTO.CategoryInfo toCategoryInfo(Category category) {
        if (category == null) {
            return null;
        }

        return HomeResDTO.CategoryInfo.builder()
                .categoryId(category.getCategoryId())
                .name(category.getName())
                .build();
    }

    private static HomeResDTO.PageInfo toPageInfo(Page<UserMission> userMissionPage) {
        return HomeResDTO.PageInfo.builder()
                .page(userMissionPage.getNumber())
                .size(userMissionPage.getSize())
                .totalElements(userMissionPage.getTotalElements())
                .totalPages(userMissionPage.getTotalPages())
                .hasNext(userMissionPage.hasNext())
                .build();
    }
}
