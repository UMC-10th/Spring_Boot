package com.example.week5_mission.domain.member.dto;

import com.example.week5_mission.domain.member.enums.Gender;
import com.example.week5_mission.domain.member.enums.SocialType;
import com.example.week5_mission.global.enums.Address;
import lombok.Getter;

import java.time.LocalDate;

public class MemberResDTO {

    @Getter
    public static class SignUpRequestBody {
        private String name;
        private Gender gender;
        private LocalDate birth;
        private Address address;
        private String detailAddress;
        private String socialUid;
        private SocialType socialType;
        private int point;
        private String email;
        private String phoneNumber;
        private String profileUrl;
    }
}
