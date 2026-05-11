package com.example.umc10th.domain.review.service;

import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.member.exception.code.MemberErrorCode;
import com.example.umc10th.domain.member.repository.MemberRepository;
import com.example.umc10th.domain.review.converter.ReviewConverter;
import com.example.umc10th.domain.review.dto.ReviewReqDTO;
import com.example.umc10th.domain.review.dto.ReviewResDTO;
import com.example.umc10th.domain.review.entity.Review;
import com.example.umc10th.domain.review.exception.code.ReviewErrorCode;
import com.example.umc10th.domain.review.repository.ReviewRepository;
import com.example.umc10th.domain.store.entity.Store;
import com.example.umc10th.domain.store.exception.code.StoreErrorCode;
import com.example.umc10th.domain.store.repository.StoreRepository;
import com.example.umc10th.global.apiPayload.exception.ProjectException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {

	private static final Long CURRENT_MEMBER_ID = 1L;

	private final ReviewRepository reviewRepository;
	private final StoreRepository storeRepository;
	private final MemberRepository memberRepository;

	public ReviewResDTO.StoreInfo getStoreInfo(Long storeId) {
		Store store = storeRepository.findById(storeId)
				.orElseThrow(() -> new ProjectException(StoreErrorCode.STORE_NOT_FOUND));

		return ReviewConverter.toStoreInfoResponse(store);
	}

	@Transactional
	public ReviewResDTO.CreateReview createReview(Long storeId, ReviewReqDTO.CreateReview request) {
		Store store = storeRepository.findById(storeId)
				.orElseThrow(() -> new ProjectException(StoreErrorCode.STORE_NOT_FOUND));

		Member member = memberRepository.findById(CURRENT_MEMBER_ID)
				.orElseThrow(() -> new ProjectException(MemberErrorCode.MEMBER_NOT_FOUND));

		Review review = Review.builder()
				.store(store)
				.member(member)
				.star(BigDecimal.valueOf(request.starRate()))
				.content(request.content())
				.createdAt(LocalDateTime.now())
				.build();

		Review savedReview = reviewRepository.save(review);

		return ReviewConverter.toCreateReviewResponse(savedReview);
	}

	public ReviewResDTO.MyReviews getMyReviews(ReviewReqDTO.MyReviewRequest request) {
		int page = request.page() == null ? 0 : request.page();
		int size = request.size() == null ? 10 : request.size();

		Page<Review> reviewPage = reviewRepository.findByMemberId(
				CURRENT_MEMBER_ID,
				PageRequest.of(page, size)
		);

		return ReviewConverter.toMyReviewsResponse(reviewPage);
	}
}