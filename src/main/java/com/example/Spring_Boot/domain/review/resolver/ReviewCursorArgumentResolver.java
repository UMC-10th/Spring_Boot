package com.example.Spring_Boot.domain.review.resolver;

import com.example.Spring_Boot.domain.review.dto.ReviewCursor;
import com.example.Spring_Boot.domain.review.enums.ReviewSortType;
import com.example.Spring_Boot.domain.review.exception.ReviewException;
import com.example.Spring_Boot.domain.review.exception.code.ReviewErrorCode;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class ReviewCursorArgumentResolver implements HandlerMethodArgumentResolver {

    private static final int DEFAULT_SIZE = 10;
    private static final int MAX_CURSOR_SIZE = 50;
    private static final String FIRST_CURSOR = "-1";

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return ReviewCursor.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory
    ) {
        ReviewSortType sortType = parseSortType(webRequest.getParameter("sort"));
        int size = parseSize(webRequest.getParameter("size"));
        String cursor = webRequest.getParameter("cursor");

        if (isFirstCursor(cursor)) {
            return ReviewCursor.builder()
                    .sortType(sortType)
                    .size(size)
                    .build();
        }

        return switch (sortType) {
            case ID -> parseIdCursor(cursor, size);
            case RATING -> parseRatingCursor(cursor, size);
        };
    }

    private ReviewSortType parseSortType(String sort) {
        if (sort == null || sort.isBlank()) {
            return ReviewSortType.ID;
        }

        try {
            return ReviewSortType.valueOf(sort.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ReviewException(ReviewErrorCode.INVALID_CURSOR_REQUEST);
        }
    }

    private int parseSize(String size) {
        if (size == null || size.isBlank()) {
            return DEFAULT_SIZE;
        }

        int parsedSize;
        try {
            parsedSize = Integer.parseInt(size);
        } catch (NumberFormatException e) {
            throw new ReviewException(ReviewErrorCode.INVALID_CURSOR_REQUEST);
        }

        if (parsedSize <= 0 || parsedSize > MAX_CURSOR_SIZE) {
            throw new ReviewException(ReviewErrorCode.INVALID_CURSOR_REQUEST);
        }

        return parsedSize;
    }

    private ReviewCursor parseIdCursor(String cursor, int size) {
        String[] parts = cursor.split(":");
        if (parts.length != 2 || !ReviewSortType.ID.name().equals(parts[0])) {
            throw new ReviewException(ReviewErrorCode.INVALID_CURSOR_REQUEST);
        }

        return ReviewCursor.builder()
                .sortType(ReviewSortType.ID)
                .reviewId(parsePositiveLong(parts[1]))
                .size(size)
                .build();
    }

    private ReviewCursor parseRatingCursor(String cursor, int size) {
        String[] parts = cursor.split(":");
        if (parts.length != 3 || !ReviewSortType.RATING.name().equals(parts[0])) {
            throw new ReviewException(ReviewErrorCode.INVALID_CURSOR_REQUEST);
        }

        Integer rating = parseInteger(parts[1]);
        if (rating < 1 || rating > 5) {
            throw new ReviewException(ReviewErrorCode.INVALID_CURSOR_REQUEST);
        }

        return ReviewCursor.builder()
                .sortType(ReviewSortType.RATING)
                .rating(rating)
                .reviewId(parsePositiveLong(parts[2]))
                .size(size)
                .build();
    }

    private boolean isFirstCursor(String cursor) {
        return cursor == null || cursor.isBlank() || FIRST_CURSOR.equals(cursor);
    }

    private Long parsePositiveLong(String value) {
        try {
            Long parsedValue = Long.parseLong(value);
            if (parsedValue <= 0) {
                throw new ReviewException(ReviewErrorCode.INVALID_CURSOR_REQUEST);
            }
            return parsedValue;
        } catch (NumberFormatException e) {
            throw new ReviewException(ReviewErrorCode.INVALID_CURSOR_REQUEST);
        }
    }

    private Integer parseInteger(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new ReviewException(ReviewErrorCode.INVALID_CURSOR_REQUEST);
        }
    }
}
