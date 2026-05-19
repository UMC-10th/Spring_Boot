package com.example.Spring_Boot.domain.review.service;

import com.example.Spring_Boot.domain.mission.converter.MissionConverter;
import com.example.Spring_Boot.domain.mission.dto.MissionResDTO;
import com.example.Spring_Boot.domain.review.converter.ReviewConverter;
import com.example.Spring_Boot.domain.review.dto.ReviewCursor;
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

    public ReviewResDTO.CreateReviewResultDTO createReview(Long userId, Long storeId,
                                                           ReviewReqDTO.CreateReviewDTO request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ProjectException(GeneralErrorCode.NOT_FOUND));
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new ProjectException(GeneralErrorCode.NOT_FOUND));
        Review review = ReviewConverter.toReview(request, user, store);
        return ReviewConverter.toCreateReviewResultDTO(reviewRepository.save(review));
    }

    @Transactional(readOnly = true)
    public MissionResDTO.Pagination<ReviewResDTO.GetReview> getMyReviews(
            Long userId, Integer pageSize, ReviewCursor reviewCursor) {

        PageRequest pageRequest = PageRequest.of(0, pageSize);
        Slice<Review> reviewList;

        if (reviewCursor.isHasCursor()) {
            switch (reviewCursor.getQuery().toLowerCase()) {
                case "id" -> reviewList = reviewRepository
                        .findReviewsByUserIdAndIdLessThanOrderByIdDesc(
                                userId, reviewCursor.getIdCursor(), pageRequest);
                case "rating" -> reviewList = reviewRepository
                        .findReviewsByUserIdAndRatingLessThanOrUserIdAndRatingEqualsAndIdLessThanOrderByRatingDescIdDesc(
                                userId, reviewCursor.getRatingCursor(),
                                userId, reviewCursor.getRatingCursor(),
                                reviewCursor.getIdCursor(), pageRequest);
                default -> throw new ProjectException(GeneralErrorCode.BAD_REQUEST);
            }
        } else {
            switch (reviewCursor.getQuery().toLowerCase()) {
                case "id" -> reviewList = reviewRepository
                        .findReviewsByUserIdOrderByIdDesc(userId, pageRequest);
                case "rating" -> reviewList = reviewRepository
                        .findReviewsByUserIdOrderByRatingDescIdDesc(userId, pageRequest);
                default -> throw new ProjectException(GeneralErrorCode.BAD_REQUEST);
            }
        }

        Review lastReview = reviewList.getContent().isEmpty() ? null
                : reviewList.getContent().get(reviewList.getContent().size() - 1);

        String nextCursor = lastReview == null ? null : switch (reviewCursor.getQuery().toLowerCase()) {
            case "id" -> lastReview.getId() + ":" + lastReview.getId();
            case "rating" -> lastReview.getRating() + ":" + lastReview.getRating() + ":" + lastReview.getId();
            default -> null;
        };

        List<ReviewResDTO.GetReview> data = reviewList.map(ReviewConverter::toGetReview).toList();
        return MissionConverter.toPagination(data, reviewList.hasNext(), nextCursor, reviewList.getSize());
    }
}