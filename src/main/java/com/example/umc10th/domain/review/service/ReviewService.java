package com.example.umc10th.domain.review.service;

import com.example.umc10th.domain.review.dto.ReviewReqDTO;
import com.example.umc10th.domain.review.dto.ReviewResDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ReviewService {

    public ReviewResDTO.CreateResult create(Long userId, ReviewReqDTO.Create request) {
        return ReviewResDTO.CreateResult.builder()
                .reviewId(1L)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
