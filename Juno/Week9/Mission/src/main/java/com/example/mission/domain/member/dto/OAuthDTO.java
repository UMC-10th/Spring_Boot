package com.example.mission.domain.member.dto;

import com.example.mission.domain.member.enums.SocialType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public abstract class OAuthDTO {
    private String socialUid;
    private String email;
    private String name;
    private SocialType socialType;
}
