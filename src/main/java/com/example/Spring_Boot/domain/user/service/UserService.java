package com.example.Spring_Boot.domain.user.service;

import com.example.Spring_Boot.domain.user.converter.UserConverter;
import com.example.Spring_Boot.domain.user.dto.UserReqDTO;
import com.example.Spring_Boot.domain.user.dto.UserResDTO;
import com.example.Spring_Boot.domain.user.entity.User;
import com.example.Spring_Boot.domain.user.exception.UserException;
import com.example.Spring_Boot.domain.user.exception.code.UserErrorCode;
import com.example.Spring_Boot.domain.user.repository.UserRepository;
import com.example.Spring_Boot.domain.user.security.AuthMember;
import com.example.Spring_Boot.global.security.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional
    public UserResDTO.JoinResultDTO join(UserReqDTO.JoinDTO request) {
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        User user = UserConverter.toUser(request, encodedPassword);
        return UserConverter.toJoinResultDTO(userRepository.save(user));
    }

    public UserResDTO.LoginResultDTO login(UserReqDTO.LoginDTO request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new UserException(UserErrorCode.INVALID_PASSWORD);
        }

        String accessToken = jwtUtil.createAccessToken(new AuthMember(user));
        return UserConverter.toLoginResultDTO(accessToken);
    }

    public UserResDTO.MyPageDTO getMyPage(AuthMember authMember) {
        return UserConverter.toMyPageDTO(authMember.getUser());
    }
}