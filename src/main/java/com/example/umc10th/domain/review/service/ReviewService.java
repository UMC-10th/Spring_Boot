package com.example.umc10th.domain.review.service;

import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.member.repository.MemberRepository;
import com.example.umc10th.domain.mission.entity.Store;
import com.example.umc10th.domain.mission.repository.StoreRepository;
import com.example.umc10th.domain.review.converter.ReviewConverter;
import com.example.umc10th.domain.review.dto.ReviewReqDTO;
import com.example.umc10th.domain.review.dto.ReviewResDTO;
import com.example.umc10th.domain.review.entity.Review;
import com.example.umc10th.domain.review.exception.ReviewException;
import com.example.umc10th.domain.review.exception.code.ReviewErrorCode;
import com.example.umc10th.domain.review.repository.ReviewRepository;
import com.example.umc10th.global.apiPayload.code.GeneralErrorCode;
import com.example.umc10th.global.cursor.Cursor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final StoreRepository storeRepository;

    // 리뷰 작성
    public ReviewResDTO.CreateReview createReview(Long storeId, ReviewReqDTO.CreateReview dto) {

        Member member = memberRepository.findById(Math.toIntExact(dto.memberId()))
                .orElseThrow(() -> new RuntimeException(GeneralErrorCode.NOT_FOUND.getMessage()));

        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new RuntimeException(GeneralErrorCode.NOT_FOUND.getMessage()));

        Review review = ReviewConverter.toReview(dto, member, store);
        Review saved = reviewRepository.save(review);

        return ReviewConverter.toCreateReview(saved);
    }

    public ReviewResDTO.Pagination<ReviewResDTO.GetMyReviews> getMyReviews(
            Long memberId,
            Cursor cursor

    ) {
        PageRequest pageRequest = cursor.toPageRequest();

        Slice<Review> reviewList;

        if(!cursor.firstPage()){
            switch(cursor.queryLowerCase()){
                case "id":
                    reviewList = reviewRepository.findReviewByMember_IdAndIdLessThanOrderByIdDesc(
                            memberId,
                            cursor.idCursor(),
                            pageRequest
                    );
                    break;
                case "star":
                    reviewList = reviewRepository.findReviewByMember_IdAndStarCursorDesc(
                            memberId,
                            cursor.valueAsFloat(),
                            cursor.idCursor(),
                            pageRequest
                    );
                    break;
                default:
                    throw new ReviewException(ReviewErrorCode.QUERY_NOT_VALID);
                }
        } else {
            if(cursor.isQuery("star")) {
                reviewList = reviewRepository.findReviewByMember_IdOrderByStarDescIdDesc(memberId, pageRequest);
            } else{
                reviewList = reviewRepository.findReviewByMember_IdOrderByIdDesc(memberId, pageRequest);
            }
        }
        List<Review> content = reviewList.getContent();
        String nextCursor = "";

        if (!content.isEmpty()) {
            Review lastReview = content.getLast();

            if (cursor.isQuery("star")) {
                nextCursor = lastReview.getStar() + ":" + lastReview.getId();
            } else {
                nextCursor = lastReview.getId() + ":" + lastReview.getId();
            }
        }


        return ReviewConverter.toPagination(
                reviewList.map(ReviewConverter::toGetMyReviews).toList(),
                reviewList.hasNext(),
                nextCursor,
                cursor.pageSize()
        );
    }
}
