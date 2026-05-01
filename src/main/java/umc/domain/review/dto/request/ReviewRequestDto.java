package umc.domain.review.dto.request;

import java.util.List;

public class ReviewRequestDto {

    public record CreateReviewDto(
            Integer star,
            String content,
            List<String> image // 이미지 URL 리스트
    ) {}
}
