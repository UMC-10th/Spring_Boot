package com.example.Spring_Boot.domain.review.service;

import com.example.Spring_Boot.domain.review.dto.ReviewReqDTO;
import com.example.Spring_Boot.domain.review.dto.ReviewResDTO;
import com.example.Spring_Boot.domain.review.repository.ReviewRepository;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public ReviewResDTO.CreateReviewResponse createReview(Integer storeId, String authorization , ReviewReqDTO.CreateReviewRequest dto) {
    }
}
