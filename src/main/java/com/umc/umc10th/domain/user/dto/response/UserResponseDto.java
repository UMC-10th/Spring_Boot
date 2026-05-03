package com.umc.umc10th.domain.user.dto.response;

import lombok.Builder;

public class UserResponseDto {
    @Builder
    public record GetInfo(
        String nickname,
        String email,
        String phone,
        int points
    ){}
}
