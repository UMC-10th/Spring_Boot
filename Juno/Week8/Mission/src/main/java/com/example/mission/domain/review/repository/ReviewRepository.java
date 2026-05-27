package com.example.mission.domain.review.repository;

import com.example.mission.domain.review.entity.Review;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Slice<Review> findReviewsByMember_IdAndIdLessThanOrderByIdDesc(Long memberId, Long idCursor, Pageable pageable);
    Slice<Review> findReviewsByMember_IdOrderByIdDesc(Long memberId, Pageable pageable);
}
