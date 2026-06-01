package com.example.Spring_Boot.global.security.oauth.dto;

import com.example.Spring_Boot.domain.member.enums.SocialType;

public record KakaoDTO(
        String socialUid,
        String email,
        String nickname
) implements OAuthDTO {

    @Override
    public SocialType provider() {
        return SocialType.KAKAO;
    }
}
