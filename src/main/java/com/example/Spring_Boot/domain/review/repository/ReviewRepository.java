package com.example.Spring_Boot.domain.review.repository;

import com.example.Spring_Boot.domain.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
