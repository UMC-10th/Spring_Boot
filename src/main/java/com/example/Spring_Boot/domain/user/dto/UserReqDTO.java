package com.example.Spring_Boot.domain.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;

public class UserReqDTO {

    @Getter
    @NoArgsConstructor
    public static class JoinDTO {
        private String name;
        private String gender;
        private String birthDate;
        private String address;
        private List<String> foodCategories;
    }
}