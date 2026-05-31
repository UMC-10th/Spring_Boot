package umc.global.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.domain.user.entity.User;
import umc.domain.user.entity.mapping.SocialType;
import umc.domain.user.repository.UserRepository;
import umc.global.security.dto.KakaoDTO;
import umc.global.security.dto.OAuthDTO;
import umc.global.security.entity.OAuthUser;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CustomOAuthService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        SocialType providerId;
        String socialUid;
        Map<String, Object> attributes = oAuth2User.getAttribute("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) attributes.get("profile");

        try {
            providerId = SocialType.valueOf(userRequest.getClientRegistration().getRegistrationId().toUpperCase());
            socialUid = String.valueOf((Long) oAuth2User.getAttribute("id"));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("지원하지 않는 소셜 로그인입니다.");
        }

        OAuthDTO dto;
        if (providerId == SocialType.KAKAO) {
            String email = attributes.get("email") != null ? attributes.get("email").toString() : "";
            String name = profile.get("nickname").toString();
            dto = new KakaoDTO(socialUid, email, name);
        } else {
            throw new IllegalArgumentException("지원하지 않는 소셜 로그인입니다.");
        }

        User user = userRepository.findBySocialTypeAndSocialUid(providerId, socialUid)
                .orElseGet(() -> {
                    User newUser = User.builder()
                            .socialType(dto.getSocialType())
                            .socialUid(dto.getSocialUid())
                            .email(dto.getSocialEmail())
                            .name(dto.getName())
                            .build();
                    return userRepository.save(newUser);
                });

        return new OAuthUser(user, oAuth2User.getAttributes());
    }
}