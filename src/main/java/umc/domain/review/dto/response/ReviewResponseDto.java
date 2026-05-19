package umc.domain.review.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

public class ReviewResponseDto {

    // 리뷰 생성 성공 시 응답
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateReviewResultDto {
        private Long reviewId;
        private LocalDateTime createdAt;
    }

    // 내가 쓴 리뷰 조회 시 응답
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MyReviewDto {
        private Long reviewId;
        private String storeName;
        private Float star;
        private String content;
        private LocalDateTime createdAt;
    }
}