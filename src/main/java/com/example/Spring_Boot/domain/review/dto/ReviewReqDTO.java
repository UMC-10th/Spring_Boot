package com.example.Spring_Boot.domain.review.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;

public class ReviewReqDTO {

    @Getter
    @NoArgsConstructor
    public static class CreateReviewDTO {
        private Integer rating;
        private String content;
        private List<String> images;
    }
}