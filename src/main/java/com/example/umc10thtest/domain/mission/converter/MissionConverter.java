package com.example.umc10thtest.domain.mission.converter;

import com.example.umc10thtest.domain.mission.dto.MissinoResDTO;
import com.example.umc10thtest.domain.mission.entity.Mission;
import com.example.umc10thtest.domain.mission.entity.mapping.MemberMission;
import com.example.umc10thtest.domain.member.dto.MemberResDTO;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class MissionConverter {

    public static MissinoResDTO.MissionPreviewRes toMissionPreviewRes(Mission mission) {
        return MissinoResDTO.MissionPreviewRes.builder()
                .missionId(mission.getId())
                .storeName(mission.getStore().getName())
                .missionSpec(mission.getMissionSpec())
                .reward(mission.getReward())
                .deadline(mission.getDeadline())
                .build();
    }

    public static MissinoResDTO.MissionPreviewListRes toMissionPreviewListRes(Page<Mission> missionPage) {
        List<MissinoResDTO.MissionPreviewRes> list = missionPage.stream()
                .map(MissionConverter::toMissionPreviewRes)
                .collect(Collectors.toList());

        return MissinoResDTO.MissionPreviewListRes.builder()
                .missionList(list)
                .listSize(list.size())
                .totalPage(missionPage.getTotalPages())
                .totalElements(missionPage.getTotalElements())
                .isFirst(missionPage.isFirst())
                .isLast(missionPage.isLast())
                .build();
    }

    public static MemberResDTO.MissionPreviewRes toMyMissionPreviewRes(MemberMission memberMission) {
        Mission mission = memberMission.getMission();
        return MemberResDTO.MissionPreviewRes.builder()
                .missionId(mission.getId())
                .storeName(mission.getStore().getName())
                .missionSpec(mission.getMissionSpec())
                .reward(mission.getReward())
                .deadline(mission.getDeadline())
                .status(memberMission.getStatus())
                .build();
    }

    public static MemberResDTO.MissionPreviewListRes toMyMissionPreviewListRes(Page<MemberMission> page) {
        List<MemberResDTO.MissionPreviewRes> list = page.stream()
                .map(MissionConverter::toMyMissionPreviewRes)
                .collect(Collectors.toList());

        return MemberResDTO.MissionPreviewListRes.builder()
                .missionList(list)
                .listSize(list.size())
                .totalPage(page.getTotalPages())
                .totalElements(page.getTotalElements())
                .isFirst(page.isFirst())
                .isLast(page.isLast())
                .build();
    }
}
