package com.example.Spring_Boot.domain.review.dto;

import com.example.Spring_Boot.domain.review.enums.ReviewSortType;
import lombok.Builder;

@Builder
public record ReviewCursor(
        ReviewSortType sortType,
        Long reviewId,
        Integer rating,
        int size
) {
}
