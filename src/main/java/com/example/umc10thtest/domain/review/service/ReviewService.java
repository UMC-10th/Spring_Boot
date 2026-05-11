package com.example.umc10thtest.domain.review.service;

import com.example.umc10thtest.domain.member.entity.Member;
import com.example.umc10thtest.domain.member.exception.MemberException;
import com.example.umc10thtest.domain.member.exception.code.MemberErrorCode;
import com.example.umc10thtest.domain.member.repository.MemberRepository;
import com.example.umc10thtest.domain.review.converter.ReviewConverter;
import com.example.umc10thtest.domain.review.dto.ReviewReqDTO;
import com.example.umc10thtest.domain.review.entity.Review;
import com.example.umc10thtest.domain.review.repository.ReviewRepository;
import com.example.umc10thtest.domain.store.entity.Store;
import com.example.umc10thtest.domain.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final StoreRepository storeRepository;

    public Review writeReview(Long storeId, Long memberId, ReviewReqDTO.WriteReviewReq request) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        Review review = ReviewConverter.toReview(request, member, store);
        return reviewRepository.save(review);
    }
}
