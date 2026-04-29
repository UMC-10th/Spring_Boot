package com.example.week5_mission.domain.review.dto;

import lombok.Getter;

public class ReviewReqDTO {

    @Getter
    public static class PostReview {
        private String content;
    }
}
