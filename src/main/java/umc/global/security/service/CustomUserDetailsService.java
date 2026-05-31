package umc.global.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import umc.domain.user.entity.User;
import umc.domain.user.entity.mapping.SocialType;
import umc.domain.user.repository.UserRepository;
import umc.global.security.entity.AuthUser;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService {
    private final UserRepository userRepository;

    public UserDetails loadUserByUidAndSocialType(SocialType socialType, String username) throws UsernameNotFoundException {
        User user = userRepository.findBySocialTypeAndSocialUid(socialType, username)
                .orElseThrow(() -> new UsernameNotFoundException("해당 유저를 찾을 수 없습니다."));

        return new AuthUser(user);
    }
}