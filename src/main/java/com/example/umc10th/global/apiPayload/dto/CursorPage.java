package com.example.umc10th.global.apiPayload.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

/**
 * 커서 기반 페이지네이션 공통 응답 DTO.
 * 오프셋과 달리 전체 개수/페이지 수를 모르고, 다음 커서만 제공한다.
 * 무한 스크롤 UI에 적합하다.
 *
 * @param <T> 페이지에 담길 데이터 타입
 */
@Getter
@Builder
public class CursorPage<T> {

    /** 현재 페이지의 실제 데이터 */
    private final List<T> items;

    /** 현재 페이지의 데이터 개수 */
    private final Integer listSize;

    /** 다음 페이지 존재 여부 */
    private final Boolean hasNext;

    /** 다음 요청 시 사용할 ID 커서 (없으면 null) */
    private final Long nextCursorId;

    /**
     * 다음 요청 시 사용할 별점 커서 (별점 정렬일 때만 채워짐).
     * ID 정렬에서는 항상 null.
     */
    private final Integer nextCursorRating;
}