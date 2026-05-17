package com.example.Spring_Boot.domain.review.service;

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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        Review saved = reviewRepository.save(review);
        return ReviewConverter.toCreateReviewResultDTO(saved);
    }
}