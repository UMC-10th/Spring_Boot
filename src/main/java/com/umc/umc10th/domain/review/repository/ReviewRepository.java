package com.umc.umc10th.domain.review.repository;


import com.umc.umc10th.domain.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("""
            SELECT r FROM Review r
            JOIN FETCH r.user u
            WHERE r.user.id = :userId
            ORDER BY r.createdAt DESC
            """)
    Page<Review> findMyReviews(
            @Param("userId") Long userId,
            Pageable pageable
    );

    @Query("""
            SELECT COUNT(r) > 0 FROM Review r
            WHERE r.store.id = :storeId AND r.user.id = :userId
            """)
    boolean existsByStoreAndUser(
            @Param("storeId") Long storeId,
            @Param("userId") Long userId
    );
}