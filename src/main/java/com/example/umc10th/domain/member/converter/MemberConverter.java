package com.example.umc10th.domain.member.converter;

import com.example.umc10th.domain.member.dto.MemberReqDTO;
import com.example.umc10th.domain.member.dto.MemberResDTO;
import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.member.entity.enums.Gender;
import com.example.umc10th.domain.member.entity.enums.SocialType;
import com.example.umc10th.domain.mission.entity.Mission;
import com.example.umc10th.domain.mission.entity.MissionParticipation;
import org.springframework.data.domain.Page;

import java.util.List;

public class MemberConverter {

    private MemberConverter() {}

    public static Member toMember(MemberReqDTO.Join req, String encodedPassword) {
        return Member.builder()
                .name(req.name())
                .email(req.email())
                .password(encodedPassword)              // BCrypt 해시값 (서비스에서 인코딩됨)
                .gender(parseGender(req.gender()))      // String → enum 변환
                .birthDate(req.birthDate())
                .address(req.address())
                .socialType(SocialType.LOCAL)           // 자체 가입 = LOCAL
                .socialUid(UUID.randomUUID().toString())// 자체 가입은 UID 무의미 → 랜덤
                .build();
    }

    // 성별 문자열을 enum으로 변환 (DTO에선 String으로 받았지만 엔티티는 enum)
    private static Gender parseGender(String value) {
        if (value == null || value.isBlank()) return Gender.MALE;  // 기본값 임시 정책
        return Gender.valueOf(value.toUpperCase());
    }

    public static MemberResDTO.JoinResult toJoinResult(Member member) {
        return MemberResDTO.JoinResult.builder()
                .memberId(member.getId())
                .createdAt(member.getCreatedAt())
                .build();
    }
    
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
