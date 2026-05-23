package com.example.umc10th.domain.review.repository;

import com.example.umc10th.domain.review.entity.Review;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Slice<Review> findReviewByMember_IdAndIdLessThanOrderByIdDesc(Long memberId, long idCursor, PageRequest pageRequest);

    Slice<Review> findReviewByMember_IdOrderByIdDesc(Long memberId, PageRequest pageRequest);

    Slice<Review> findReviewByMember_IdOrderByStarDescIdDesc(Long memberId, PageRequest pageRequest);

    @Query("SELECT r FROM Review r WHERE r.member.id = :memberId AND " +
            "(r.star < :star OR (r.star = :star AND r.id < :idCursor)) " +
            "ORDER BY r.star DESC, r.id DESC")
    Slice<Review> findReviewByMember_IdAndStarCursorDesc(
            @Param("memberId") Long memberId,
            @Param("star") Float star,
            @Param("idCursor") Long idCursor,
            PageRequest pageRequest
    );
}
