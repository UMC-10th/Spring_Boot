package com.example.Spring_Boot.global.resolver;

import com.example.Spring_Boot.domain.review.dto.ReviewCursor;
import com.example.Spring_Boot.global.apiPayload.code.GeneralErrorCode;
import com.example.Spring_Boot.global.exception.ProjectException;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class ReviewCursorArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(ReviewCursor.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) {

        String cursor = webRequest.getParameter("cursor");
        String query = webRequest.getParameter("query");

        if (cursor == null) cursor = "-1";
        if (query == null) query = "id";

        if (cursor.equals("-1")) {
            return ReviewCursor.builder()
                    .query(query)
                    .cursor(cursor)
                    .hasCursor(false)
                    .build();
        }

        String[] cursorSplit = cursor.split(":");

        switch (query.toLowerCase()) {
            case "id" -> {
                Long idCursor = Long.parseLong(cursorSplit[1]);
                return ReviewCursor.builder()
                        .query(query)
                        .cursor(cursor)
                        .idCursor(idCursor)
                        .hasCursor(true)
                        .build();
            }
            case "rating" -> {
                Integer ratingCursor = Integer.parseInt(cursorSplit[1]);
                Long idCursor = Long.parseLong(cursorSplit[2]);
                return ReviewCursor.builder()
                        .query(query)
                        .cursor(cursor)
                        .ratingCursor(ratingCursor)
                        .idCursor(idCursor)
                        .hasCursor(true)
                        .build();
            }
            default -> throw new ProjectException(GeneralErrorCode.BAD_REQUEST);
        }
    }
}