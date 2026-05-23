package umc.domain.user.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;

public class UserResponseDto {
    @Builder
    public record SignUpResultDto(
            Long userId,
            LocalDateTime createdAt
    ) {}

    @Builder
    public record HomeResultDto (
            String welcomeMessage
    ) {}
}
