package com.example.Spring_Boot.domain.review.repository;

import com.example.Spring_Boot.domain.review.entity.Review;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("""
            select r
            from Review r
            join fetch r.store s
            where r.member.userId = :memberId
              and (:cursorId is null or r.reviewId < :cursorId)
            order by r.reviewId desc
            """)
    Slice<Review> findMyReviewsOrderByIdDesc(
            @Param("memberId") Long memberId,
            @Param("cursorId") Long cursorId,
            Pageable pageable
    );

    @Query("""
            select r
            from Review r
            join fetch r.store s
            where r.member.userId = :memberId
              and (
                    :cursorRating is null
                    or r.rating < :cursorRating
                    or (r.rating = :cursorRating and r.reviewId < :cursorId)
                  )
            order by r.rating desc, r.reviewId desc
            """)
    Slice<Review> findMyReviewsOrderByRatingDesc(
            @Param("memberId") Long memberId,
            @Param("cursorRating") Integer cursorRating,
            @Param("cursorId") Long cursorId,
            Pageable pageable
    );
}
