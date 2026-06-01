package com.example.Spring_Boot.domain.user.service;

import com.example.Spring_Boot.domain.user.converter.UserConverter;
import com.example.Spring_Boot.domain.user.dto.KakaoDTO;
import com.example.Spring_Boot.domain.user.dto.OAuthDTO;
import com.example.Spring_Boot.domain.user.entity.User;
import com.example.Spring_Boot.domain.user.enums.SocialType;
import com.example.Spring_Boot.domain.user.repository.UserRepository;
import com.example.Spring_Boot.domain.user.security.OAuthMember;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CustomOAuthService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuthUser = super.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId().toUpperCase();
        SocialType providerId = SocialType.valueOf(registrationId);
        String socialUid = String.valueOf((Long) oAuthUser.getAttribute("id"));

        Map<String, Object> attributes = oAuthUser.getAttribute("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) attributes.get("profile");

        OAuthDTO dto;
        switch (providerId) {
            case KAKAO -> {
                String email = attributes.get("email").toString();
                String name = profile.get("nickname").toString();
                dto = new KakaoDTO(socialUid, email, name);
            }
            default -> throw new OAuth2AuthenticationException("지원하지 않는 소셜 로그인입니다.");
        }

        User user = userRepository.findBySocialTypeAndSocialUid(providerId, socialUid)
                .orElseGet(() -> {
                    User newUser = UserConverter.toOAuthUser(dto, providerId);
                    return userRepository.save(newUser);
                });

        return new OAuthMember(user, oAuthUser.getAttributes());
    }
}