package com.example.mission.domain.member.dto;

import com.example.mission.domain.member.enums.Gender;
import lombok.Getter;

import java.util.List;

public class MemberReqDTO {

    @Getter
    public static class JoinDTO {
        private String email;
        private String password;
        private String name;
        private Gender gender;
        private String address;
        private List<Long> preferCategory;
    }

    @Getter
    public static class LoginDTO {
        private String email;
        private String password;
    }
}
