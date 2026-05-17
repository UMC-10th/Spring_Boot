package com.example.mission.domain.store.dto;

import lombok.Getter;

import java.time.LocalDate;

public class StoreReqDTO {

    @Getter
    public static class PostReview {
        private String content;
    }
}
