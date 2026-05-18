package com.example.Spring_Boot.domain.review.repository;

import com.example.Spring_Boot.domain.review.entity.Review;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    // ID 순 (커서 없는 경우)
    Slice<Review> findReviewsByUserIdOrderByIdDesc(Long userId, Pageable pageable);

    // ID 순 (커서 있는 경우)
    Slice<Review> findReviewsByUserIdAndIdLessThanOrderByIdDesc(Long userId, Long idCursor, Pageable pageable);

    // 별점 순 (커서 없는 경우)
    Slice<Review> findReviewsByUserIdOrderByRatingDescIdDesc(Long userId, Pageable pageable);

    // 별점 순 (커서 있는 경우) - 복합 커서
    Slice<Review> findReviewsByUserIdAndRatingLessThanOrUserIdAndRatingEqualsAndIdLessThanOrderByRatingDescIdDesc(
            Long userId1, Integer rating, Long userId2, Integer ratingEq, Long idCursor, Pageable pageable);
}