package com.example.umc10th.global.cursor;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class CursorArgumentResolver implements HandlerMethodArgumentResolver {

    private static final String FIRST_PAGE_CURSOR = "-1";
    private static final String DEFAULT_QUERY = "id";

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return Cursor.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory
    ) {
        Integer pageSize = Integer.parseInt(webRequest.getParameter("pageSize"));
        String cursor = webRequest.getParameter("cursor");
        String query = webRequest.getParameter("query");

        if (cursor == null || cursor.isBlank()) {
            cursor = FIRST_PAGE_CURSOR;
        }
        if (query == null || query.isBlank()) {
            query = DEFAULT_QUERY;
        }

        if (FIRST_PAGE_CURSOR.equals(cursor)) {
            return new Cursor(pageSize, query, null, null, true);
        }

        String[] cursorSplit = cursor.split(":");
        if (cursorSplit.length != 2) {
            throw new IllegalArgumentException("Invalid cursor format");
        }

        return new Cursor(
                pageSize,
                query,
                cursorSplit[0],
                Long.parseLong(cursorSplit[1]),
                false
        );
    }
}
