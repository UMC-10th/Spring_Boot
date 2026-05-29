package com.example.Spring_Boot.domain.review.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReviewCursor {
    private final String query;
    private final String cursor;
    private final Long idCursor;
    private final Integer ratingCursor;
    private final boolean hasCursor;
}