package com.example.Spring_Boot.global.security.oauth;

import com.example.Spring_Boot.domain.member.converter.MemberConverter;
import com.example.Spring_Boot.domain.member.entity.Member;
import com.example.Spring_Boot.domain.member.enums.SocialType;
import com.example.Spring_Boot.domain.member.exception.MemberException;
import com.example.Spring_Boot.domain.member.exception.code.MemberErrorCode;
import com.example.Spring_Boot.domain.member.repository.MemberRepository;
import com.example.Spring_Boot.global.security.oauth.dto.KakaoDTO;
import com.example.Spring_Boot.global.security.oauth.dto.OAuthDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomOAuthService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        SocialType provider = getProvider(userRequest);
        OAuthDTO oAuthDTO = switch (provider) {
            case KAKAO -> toKakaoDTO(oAuth2User);
            default -> throw new MemberException(MemberErrorCode.NOT_SUPPORT_SOCIAL_PROVIDER);
        };

        Member member = findOrCreateMember(provider, oAuthDTO);

        return new OAuthMember(member, oAuth2User.getAttributes());
    }

    private Member findOrCreateMember(SocialType provider, OAuthDTO oAuthDTO) {
        return memberRepository.findBySocialProviderAndSocialUid(provider, oAuthDTO.socialUid())
                .or(() -> findByEmail(oAuthDTO.email())
                        .map(member -> linkSocialAccount(member, provider, oAuthDTO.socialUid())))
                .orElseGet(() -> memberRepository.save(MemberConverter.toMember(oAuthDTO)));
    }

    private Optional<Member> findByEmail(String email) {
        if (!StringUtils.hasText(email)) {
            return Optional.empty();
        }

        return memberRepository.findByEmail(email);
    }

    private Member linkSocialAccount(Member member, SocialType provider, String socialUid) {
        member.linkSocialAccount(provider, socialUid);
        return memberRepository.save(member);
    }

    private SocialType getProvider(OAuth2UserRequest userRequest) {
        try {
            return SocialType.valueOf(
                    userRequest.getClientRegistration().getRegistrationId().toUpperCase()
            );
        } catch (IllegalArgumentException e) {
            throw new MemberException(MemberErrorCode.NOT_SUPPORT_SOCIAL_PROVIDER);
        }
    }

    private OAuthDTO toKakaoDTO(OAuth2User oAuth2User) {
        String socialUid = getRequiredString(oAuth2User.getAttributes().get("id"), "id");
        Map<String, Object> kakaoAccount = getAttributeMap(oAuth2User.getAttributes().get("kakao_account"));
        Map<String, Object> profile = getAttributeMap(kakaoAccount.get("profile"));

        String email = getEmail(kakaoAccount, socialUid);
        String nickname = getString(profile.get("nickname"), "카카오유저");

        return new KakaoDTO(socialUid, email, nickname);
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> getAttributeMap(Object value) {
        if (value instanceof Map<?, ?> map) {
            return (Map<String, Object>) map;
        }

        return Collections.emptyMap();
    }

    private String getEmail(Map<String, Object> kakaoAccount, String socialUid) {
        String email = getString(kakaoAccount.get("email"), null);
        if (StringUtils.hasText(email)) {
            return email;
        }

        return "kakao_" + socialUid + "@oauth.local";
    }

    private String getString(Object value, String defaultValue) {
        if (value == null) {
            return defaultValue;
        }

        String text = String.valueOf(value);
        return StringUtils.hasText(text) ? text : defaultValue;
    }

    private String getRequiredString(Object value, String attributeName) {
        String text = getString(value, null);
        if (!StringUtils.hasText(text)) {
            throw new OAuth2AuthenticationException(
                    new OAuth2Error("invalid_user_info"),
                    "카카오 사용자 정보에서 " + attributeName + " 값을 찾을 수 없습니다."
            );
        }

        return text;
    }
}
