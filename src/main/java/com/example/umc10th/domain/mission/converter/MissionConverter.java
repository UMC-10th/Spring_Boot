package com.example.umc10th.domain.mission.converter;

import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.mission.dto.MissionReqDTO;
import com.example.umc10th.domain.mission.dto.MissionResDTO;
import com.example.umc10th.domain.mission.entity.Mission;
import com.example.umc10th.domain.mission.entity.Store;
import com.example.umc10th.domain.mission.entity.mapping.MemberMission;
import org.springframework.data.domain.Page;

import java.util.List;

public class MissionConverter {

    // 홈 화면 - 아직 도전 전
    public static MissionResDTO.MissionPreview toMissionPreview(Mission mission) {
        return MissionResDTO.MissionPreview.builder()
                .memberMissionId(null)
                .missionId(mission.getId())
                .storeName(mission.getStore().getName())
                .conditional(mission.getConditional())
                .point(mission.getPoint())
                .deadline(mission.getDeadline())
                .isComplete(false)
                .build();
    }

    // 내 미션 목록
    public static MissionResDTO.MissionPreview toMissionPreview(MemberMission memberMission) {
        return MissionResDTO.MissionPreview.builder()
                .memberMissionId(memberMission.getId())
                .missionId(memberMission.getMission().getId())
                .storeName(memberMission.getMission().getStore().getName())
                .conditional(memberMission.getMission().getConditional())
                .point(memberMission.getMission().getPoint())
                .deadline(memberMission.getMission().getDeadline())
                .isComplete(memberMission.getIsComplete())
                .build();
    }

    // 홈 화면 응답
    public static MissionResDTO.GetHome toGetHome(Member member, Page<Mission> missionPage) {
        List<MissionResDTO.MissionPreview> previews = missionPage.getContent().stream()
                .map(MissionConverter::toMissionPreview)
                .toList();

        return MissionResDTO.GetHome.builder()
                .memberName(member.getName())
                .point(member.getPoint())
                .missions(previews)
                .build();
    }

    // 내 미션 목록 응답
    public static MissionResDTO.GetMyMissions toGetMyMissions(Page<MemberMission> memberMissionPage) {
        List<MissionResDTO.MissionPreview> previews = memberMissionPage.getContent().stream()
                .map(MissionConverter::toMissionPreview)
                .toList();

        return MissionResDTO.GetMyMissions.builder()
                .missions(previews)
                .totalCount((int) memberMissionPage.getTotalElements())
                .build();
    }

    // 가게 미션 생성
    public static Mission toMission(
            Store store,
            MissionReqDTO .CreateMission dto
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

    // 페이지네이션 틀 생성
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
