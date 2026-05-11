package umc.domain.review.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;

public class ReviewResponseDto {

    @Builder
    public record CreateReviewResultDto(
            Long reviewId,
            LocalDateTime createdAt
    ) {}
}
