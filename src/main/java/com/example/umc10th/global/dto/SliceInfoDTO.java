package com.example.umc10th.global.dto;

import lombok.Builder;

@Builder
public record SliceInfoDTO (
        String nextCursor,
        Boolean hasNext,
        Integer numberOfElements
) {}