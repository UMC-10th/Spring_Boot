package com.example.Spring_Boot.global.security.oauth.dto;

import com.example.Spring_Boot.domain.member.enums.SocialType;

public interface OAuthDTO {

    SocialType provider();

    String socialUid();

    String email();

    String nickname();
}
