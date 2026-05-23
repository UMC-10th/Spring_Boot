package umc.domain.review.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.web.bind.annotation.*;
import umc.domain.review.dto.request.ReviewRequestDto;
import umc.domain.review.dto.response.ReviewResponseDto;
import umc.domain.review.entity.Review;
import umc.domain.review.service.ReviewCommandServiceImpl;
import umc.domain.review.service.ReviewQueryServiceImpl;
import umc.global.apiPayload.ApiResponse;
import umc.global.apiPayload.code.status.GeneralSuccessCode;
import umc.global.common.CursorResponseDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {
    private final ReviewQueryServiceImpl reviewQueryService;
    private final ReviewCommandServiceImpl reviewCommandService;
    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    @PostMapping("/missions/{missionId}/reviews")
    public ApiResponse<ReviewResponseDto.CreateReviewResultDto> createReview(
            @RequestHeader(name = "userId") Long userId,
            @PathVariable(name = "missionId") Long missionId,
            @RequestBody @Valid ReviewRequestDto.CreateReviewDto request
    ) {
        Review review = reviewCommandService.createReview(userId, missionId, request);

        ReviewResponseDto.CreateReviewResultDto response = ReviewResponseDto.CreateReviewResultDto.builder()
                .reviewId(review.getId())
                .createdAt(review.getCreatedAt())
                .build();

        return ApiResponse.onSuccess(GeneralSuccessCode.OK, response);
    }

    @GetMapping("/my")
    public ApiResponse<CursorResponseDto<ReviewResponseDto.MyReviewDto>> getMyReviews(
            @RequestHeader(name = "userId") Long userId,
            @RequestParam(name = "cursor", defaultValue = "-1") String cursor,
            @RequestParam(name = "query", defaultValue = "id") String query,
            @RequestParam(name = "size", defaultValue = "10") Integer size
    ) {
        Slice<Review> reviewSlice = reviewQueryService.getMyReviews(userId, cursor, query, size);

        List<ReviewResponseDto.MyReviewDto> data = reviewSlice.stream()
                .map(r -> ReviewResponseDto.MyReviewDto.builder()
                        .reviewId(r.getId())
                        .storeName(r.getStore().getName())
                        .star(r.getStar())
                        .content(r.getContent())
                        .createdAt(r.getCreatedAt())
                        .build())
                .collect(Collectors.toList());

        String nextCursor = null;
        if (reviewSlice.hasNext() && !data.isEmpty()) {
            Review lastReview = reviewSlice.getContent().get(reviewSlice.getContent().size() - 1);
            if (query.equalsIgnoreCase("star")) {
                nextCursor = lastReview.getStar() + ":" + lastReview.getId();
            } else {
                nextCursor = String.valueOf(lastReview.getId());
            }
        }

        CursorResponseDto<ReviewResponseDto.MyReviewDto> response = CursorResponseDto.<ReviewResponseDto.MyReviewDto>builder()
                .data(data)
                .hasNext(reviewSlice.hasNext())
                .nextCursor(nextCursor)
                .pageSize(reviewSlice.getSize())
                .build();

        return ApiResponse.onSuccess(GeneralSuccessCode.OK, response);
    }
}
