package com.example.Spring_Boot.domain.user.converter;

import com.example.Spring_Boot.domain.user.dto.UserResDTO;
import com.example.Spring_Boot.domain.user.entity.User;

public class UserConverter {

    public static UserResDTO.MyPageDTO toMyPageDTO(User user) {
        return UserResDTO.MyPageDTO.builder()
                .userId(user.getId())
                .name(user.getName())
                .point(user.getPoint())
                .gender(user.getGender() != null ? user.getGender().name() : null)
                .build();
    }
}