package com.example.Spring_Boot.domain.review.service;

import com.example.Spring_Boot.domain.mission.converter.MissionConverter;
import com.example.Spring_Boot.domain.mission.dto.MissionResDTO;
import com.example.Spring_Boot.domain.review.converter.ReviewConverter;
import com.example.Spring_Boot.domain.review.dto.ReviewReqDTO;
import com.example.Spring_Boot.domain.review.dto.ReviewResDTO;
import com.example.Spring_Boot.domain.review.entity.Review;
import com.example.Spring_Boot.domain.review.repository.ReviewRepository;
import com.example.Spring_Boot.domain.store.entity.Store;
import com.example.Spring_Boot.domain.store.repository.StoreRepository;
import com.example.Spring_Boot.domain.user.entity.User;
import com.example.Spring_Boot.domain.user.repository.UserRepository;
import com.example.Spring_Boot.global.apiPayload.code.GeneralErrorCode;
import com.example.Spring_Boot.global.exception.ProjectException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;

    // 기존: 리뷰 작성
    public ReviewResDTO.CreateReviewResultDTO createReview(Long userId, Long storeId,
                                                           ReviewReqDTO.CreateReviewDTO request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ProjectException(GeneralErrorCode.NOT_FOUND));
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new ProjectException(GeneralErrorCode.NOT_FOUND));
        Review review = ReviewConverter.toReview(request, user, store);
        return ReviewConverter.toCreateReviewResultDTO(reviewRepository.save(review));
    }

    // 미션 2: 내가 생성한 리뷰 조회 (커서 기반)
    @Transactional(readOnly = true)
    public MissionResDTO.Pagination<ReviewResDTO.GetReview> getMyReviews(
            Long userId, Integer pageSize, String cursor, String query) {

        PageRequest pageRequest = PageRequest.of(0, pageSize);

        long idCursor;
        Slice<Review> reviewList;
        String nextCursor;

        // 커서가 있는 경우
        if (!cursor.equals("-1")) {

            // 커서 분리: "id:10" or "rating:4:10"
            String[] cursorSplit = cursor.split(":");

            switch (query.toLowerCase()) {
                case "id" -> {
                    // 커서 타입 변환
                    idCursor = Long.parseLong(cursorSplit[1]);
                    // ID 순 조회
                    reviewList = reviewRepository.findReviewsByUserIdAndIdLessThanOrderByIdDesc(
                            userId, idCursor, pageRequest);
                }
                case "rating" -> {
                    // 커서 타입 변환
                    Integer ratingCursor = Integer.parseInt(cursorSplit[1]);
                    idCursor = Long.parseLong(cursorSplit[2]);
                    // 별점 순 조회 (복합 커서)
                    reviewList = reviewRepository
                            .findReviewsByUserIdAndRatingLessThanOrUserIdAndRatingEqualsAndIdLessThanOrderByRatingDescIdDesc(
                                    userId, ratingCursor, userId, ratingCursor, idCursor, pageRequest);
                }
                default -> throw new ProjectException(GeneralErrorCode.BAD_REQUEST);
            }
        } else {
            // 커서 없이 조회
            switch (query.toLowerCase()) {
                case "id" -> reviewList = reviewRepository
                        .findReviewsByUserIdOrderByIdDesc(userId, pageRequest);
                case "rating" -> reviewList = reviewRepository
                        .findReviewsByUserIdOrderByRatingDescIdDesc(userId, pageRequest);
                default -> throw new ProjectException(GeneralErrorCode.BAD_REQUEST);
            }
        }

        // 다음 커서 계산: ID:ID 형태 or rating:rating:ID 형태
        Review lastReview = reviewList.getContent().isEmpty() ? null
                : reviewList.getContent().get(reviewList.getContent().size() - 1);

        nextCursor = lastReview == null ? null : switch (query.toLowerCase()) {
            case "id" -> lastReview.getId() + ":" + lastReview.getId();
            case "rating" -> lastReview.getRating() + ":" + lastReview.getRating() + ":" + lastReview.getId();
            default -> null;
        };

        List<ReviewResDTO.GetReview> data = reviewList.map(ReviewConverter::toGetReview).toList();

        return MissionConverter.toPagination(data, reviewList.hasNext(), nextCursor, reviewList.getSize());
    }
}