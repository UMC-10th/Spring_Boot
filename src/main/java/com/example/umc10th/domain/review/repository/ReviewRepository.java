package com.example.umc10th.domain.review.repository;

import com.example.umc10th.domain.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends JpaRepository <Review, Long> {

    // 내가 작성한 리뷰 목록 (페이징 + Fetch Join)
    @Query("SELECT r FROM Review r " +
            "JOIN FETCH r.store " +
            "WHERE r.member.id = :memberId")
    Page<Review> findByMemberId(
            @Param("memberId") Long memberId,
            Pageable pageable
    );
}
