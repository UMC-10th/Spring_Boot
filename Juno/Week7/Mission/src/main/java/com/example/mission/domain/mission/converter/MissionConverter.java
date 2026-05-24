package com.example.mission.domain.mission.converter;

import com.example.mission.domain.mission.dto.MissionReqDTO;
import com.example.mission.domain.mission.dto.MissionResDTO;
import com.example.mission.domain.mission.entity.Mission;
import com.example.mission.domain.store.entity.Store;

import java.util.List;

public class MissionConverter {

    // 가게 미션 생성
    public static Mission toMission(
            Store store,
            MissionReqDTO.CreateMission dto
    ){
        return Mission.builder()
                .store(store)
                .conditional(dto.conditional())
                .point(dto.point())
                .deadline(dto.deadline())
                .build();
    }

    // 가게 내 미션 조회
    public static MissionResDTO.GetMission toGetMission(
            Mission mission
    ){
        return MissionResDTO.GetMission.builder()
                .conditional(mission.getConditional())
                .point(mission.getPoint())
                .missionId(mission.getId())
                .build();
    }

    public static MissionResDTO.GetMemberMission toGetMemberMission(
            com.example.mission.domain.mission.entity.mapping.MemberMission memberMission
    ){
        return MissionResDTO.GetMemberMission.builder()
                .memberMissionId(memberMission.getId())
                .status(memberMission.getStatus())
                .mission(toGetMission(memberMission.getMission()))
                .build();
    }

    // 페이지네이션 틀 생성: 커서 기반
    public static <T> MissionResDTO.Pagination<T> toPagination(
            List<T> data,
            Boolean hasNext,
            String nextCursor,
            Integer pageSize
    ){
        return MissionResDTO.Pagination.<T>builder()
                .data(data)
                .hasNext(hasNext)
                .nextCursor(nextCursor)
                .pageSize(pageSize)
                .build();
    }
}
