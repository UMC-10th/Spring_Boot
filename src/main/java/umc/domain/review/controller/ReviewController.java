package umc.domain.review.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import umc.domain.review.dto.request.ReviewRequestDto;
import umc.domain.review.dto.response.ReviewResponseDto;
import umc.global.apiPayload.ApiResponse;
import umc.global.apiPayload.code.status.GeneralSuccessCode;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    @PostMapping("/missions/{missionId}/reviews")
    public ApiResponse<ReviewResponseDto.CreateReviewResultDto> createReview(
            @PathVariable(name = "missionId") Long missionId,
            @RequestBody ReviewRequestDto.CreateReviewDto request
    ) {
        ReviewResponseDto.CreateReviewResultDto dummyResponse = ReviewResponseDto.CreateReviewResultDto.builder()
                .reviewId(100L)
                .createdAt(LocalDateTime.now())
                .build();
        return ApiResponse.onSuccess(GeneralSuccessCode.OK, dummyResponse);
    }
}
