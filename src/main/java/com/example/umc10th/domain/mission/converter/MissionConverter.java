package com.example.umc10th.domain.mission.converter;

import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.mission.dto.MssionResDTO;
import com.example.umc10th.domain.mission.entity.Mission;
import com.example.umc10th.domain.mission.entity.mapping.MemberMission;
import org.springframework.data.domain.Page;

import java.util.List;

public class MissionConverter {

    // 홈 화면 - 아직 도전 전
    public static MssionResDTO.MissionPreview toMissionPreview(Mission mission) {
        return MssionResDTO.MissionPreview.builder()
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
    public static MssionResDTO.MissionPreview toMissionPreview(MemberMission memberMission) {
        return MssionResDTO.MissionPreview.builder()
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
    public static MssionResDTO.GetHome toGetHome(Member member, Page<Mission> missionPage) {
        List<MssionResDTO.MissionPreview> previews = missionPage.getContent().stream()
                .map(MissionConverter::toMissionPreview)
                .toList();

        return MssionResDTO.GetHome.builder()
                .memberName(member.getName())
                .point(member.getPoint())
                .missions(previews)
                .build();
    }

    // 내 미션 목록 응답
    public static MssionResDTO.GetMyMissions toGetMyMissions(Page<MemberMission> memberMissionPage) {
        List<MssionResDTO.MissionPreview> previews = memberMissionPage.getContent().stream()
                .map(MissionConverter::toMissionPreview)
                .toList();

        return MssionResDTO.GetMyMissions.builder()
                .missions(previews)
                .totalCount((int) memberMissionPage.getTotalElements())
                .build();
    }

}
