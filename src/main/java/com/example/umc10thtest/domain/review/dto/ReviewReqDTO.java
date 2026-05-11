package com.example.umc10thtest.domain.review.dto;

import lombok.Getter;

public class ReviewReqDTO {

    @Getter
    public static class WriteReviewReq {
        private Float score;
        private String body;
    }
}
