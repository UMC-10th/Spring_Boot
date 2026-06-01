package com.example.Spring_Boot.domain.user.converter;

import com.example.Spring_Boot.domain.mission.enums.Address;
import com.example.Spring_Boot.domain.user.dto.UserReqDTO;
import com.example.Spring_Boot.domain.user.dto.UserResDTO;
import com.example.Spring_Boot.domain.user.entity.User;
import com.example.Spring_Boot.domain.user.enums.Gender;
import com.example.Spring_Boot.domain.user.enums.SocialType;
import com.example.Spring_Boot.domain.user.dto.OAuthDTO;
import java.time.LocalDate;

public class UserConverter {

    public static User toUser(UserReqDTO.JoinDTO request, String encodedPassword) {
        return User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(encodedPassword)
                .gender(request.getGender() != null ? Gender.valueOf(request.getGender()) : Gender.NONE)
                .birth(LocalDate.now())
                .address(Address.SEOUL)
                .detailAddress("")
                .socialUid("")
                .socialType(SocialType.NONE)
                .build();
    }

    public static User toOAuthUser(OAuthDTO dto, SocialType socialType) {
        return User.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .password("")
                .gender(Gender.NONE)
                .birth(LocalDate.now())
                .address(Address.SEOUL)
                .detailAddress("")
                .socialUid(dto.getSocialUid())
                .socialType(socialType)
                .build();
    }

    public static UserResDTO.JoinResultDTO toJoinResultDTO(User user) {
        return UserResDTO.JoinResultDTO.builder()
                .userId(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }

    public static UserResDTO.LoginResultDTO toLoginResultDTO(String accessToken) {
        return UserResDTO.LoginResultDTO.builder()
                .accessToken(accessToken)
                .build();
    }

    public static UserResDTO.MyPageDTO toMyPageDTO(User user) {
        return UserResDTO.MyPageDTO.builder()
                .userId(user.getId())
                .name(user.getName())
                .point(user.getPoint())
                .gender(user.getGender() != null ? user.getGender().name() : null)
                .build();
    }
}