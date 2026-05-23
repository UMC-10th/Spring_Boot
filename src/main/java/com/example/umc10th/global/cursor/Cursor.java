package com.example.umc10th.global.cursor;

import org.springframework.data.domain.PageRequest;

public record Cursor(
        Integer pageSize,
        String query,
        String value,
        Long idCursor,
        boolean firstPage
) {

    public PageRequest toPageRequest() {
        return PageRequest.of(0, pageSize);
    }

    public boolean isQuery(String target) {
        return query.equalsIgnoreCase(target);
    }

    public String queryLowerCase() {
        return query.toLowerCase();
    }

    public Float valueAsFloat() {
        return Float.parseFloat(value);
    }
}
