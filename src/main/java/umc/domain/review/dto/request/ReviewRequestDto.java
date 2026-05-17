package umc.domain.review.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public class ReviewRequestDto {

    public record CreateReviewDto(
            @NotNull(message = "별점은 필수입니다.")
            @Min(value = 1, message = "최소 별점은 1점입니다.  ")
            @Max(value = 5, message = "최대 별점은 5점입니다. ")
            Integer star,

            @NotBlank(message = "리뷰 내용은 필수입니다.")
            String content,

            List<String> image
    ) {}
}