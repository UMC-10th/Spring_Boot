package com.example.umc10th.domain.member.converter;

import com.example.umc10th.domain.member.dto.MemberReqDTO;
import com.example.umc10th.domain.member.dto.MemberResDTO;
import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.mission.entity.Mission;
import com.example.umc10th.domain.mission.entity.MissionParticipation;
import org.springframework.data.domain.Page;

import java.util.List;

public class MemberConverter {

    private MemberConverter() {}

    public static MemberResDTO.MyPage toMyPage(Member member, long reviewCount) {
        return MemberResDTO.MyPage.builder()
                .memberId(member.getId())
                .name(member.getName())
                .email(member.getEmail())
                .point(member.getPoint())
                .reviewCount((int) reviewCount)
                .build();
    }

    public static MemberResDTO.MissionListItem toMissionListItem(MissionParticipation p) {
        Mission mission = p.getMission();
        return MemberResDTO.MissionListItem.builder()
                .missionId(mission.getMissionId())
                .title(mission.getMissionName())
                .pointReward(mission.getPointReward())
                .missionStatus(p.getMissionStatus().name())
                .restaurantName(mission.getRestaurant().getRestaurantName())
                .description(mission.getDescription())
                .build();
    }

    public static MemberResDTO.MissionList toMissionList(Page<MissionParticipation> page) {
        List<MemberResDTO.MissionListItem> items = page.getContent().stream()
                .map(MemberConverter::toMissionListItem)
                .toList();

        return MemberResDTO.MissionList.builder()
                .missions(items)
                .totalCount(page.getTotalElements())
                .currentPage(page.getNumber() + 1) // 페이지 번호는 0부터 시작하므로 1을 더해줌
                .build();
    }

//    public static Member toMember(MemberReqDTO.Join joinDTO) {
//        return Member.builder()
//                .name(joinDTO.name())
//                .email(joinDTO.email())
//                .password(joinDTO.password())   Member 엔티티에서 패스워드는 해싱되어 저장되어야 하므로, 실제로는 서비스 레이어에서 해싱 작업이 필요함
//                .gender(joinDTO.gender())
//                .birthDate(joinDTO.birthDate())
//                .address(joinDTO.address())
//                .build();
//    }
}
