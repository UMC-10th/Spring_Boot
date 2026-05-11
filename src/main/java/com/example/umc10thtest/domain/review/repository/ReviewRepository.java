package com.example.umc10thtest.domain.review.repository;

import com.example.umc10thtest.domain.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
