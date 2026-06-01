package umc.global.security.dto;

import umc.domain.user.entity.mapping.SocialType;

public interface OAuthDTO {
    SocialType getSocialType();
    String getSocialUid();
    String getSocialEmail();
    String getName();
}