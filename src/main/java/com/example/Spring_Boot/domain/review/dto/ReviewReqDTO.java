package com.example.Spring_Boot.domain.review.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;

public class ReviewReqDTO {

    @Getter
    @NoArgsConstructor
    public static class CreateReviewDTO {

        @NotNull(message = "별점은 필수입니다.")
        @Min(value = 1, message = "별점은 1 이상이어야 합니다.")
        @Max(value = 5, message = "별점은 5 이하여야 합니다.")
        private Integer rating;

        @NotBlank(message = "내용은 필수입니다.")
        @Size(min = 1, max = 500, message = "내용은 1자 이상 500자 이하여야 합니다.")
        private String content;

        private List<String> images;
    }
}