package com.grasshouse.dorandoran.post.controller;

import static com.grasshouse.dorandoran.common.exception.DateNotValidException.NOT_VALID_DATE_AREA_MESSAGE;
import static com.grasshouse.dorandoran.common.exception.DateNotValidException.NOT_VALID_DATE_FORMAT_MESSAGE;

import com.grasshouse.dorandoran.common.exception.DateNotValidException;
import com.grasshouse.dorandoran.post.dto.PostFilterRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class PostFilterArgumentResolver implements HandlerMethodArgumentResolver {

    private static final String DATE_FORMAT = "[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}";
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter
        .ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(PostFilter.class);
    }

    @Override
    public Object resolveArgument(
        MethodParameter parameter,
        ModelAndViewContainer mavContainer,
        NativeWebRequest webRequest,
        WebDataBinderFactory binderFactory
    ) {
        String keyword = webRequest.getParameter("keyword");
        String startDate = webRequest.getParameter("startDate");
        String endDate = webRequest.getParameter("endDate");

        if (StringUtils.isEmpty(startDate) || StringUtils.isEmpty(endDate)) {
            return new PostFilterRequest(keyword, null, null);
        }

        if (!startDate.matches(DATE_FORMAT) || !endDate.matches(DATE_FORMAT)) {
            throw new DateNotValidException(NOT_VALID_DATE_FORMAT_MESSAGE);
        }

        LocalDateTime parsedStartDate = LocalDateTime.parse(startDate, DATE_TIME_FORMATTER);
        LocalDateTime parsedEndDate = LocalDateTime.parse(endDate, DATE_TIME_FORMATTER);

        if (parsedStartDate.isAfter(parsedEndDate)) {
            throw new DateNotValidException(NOT_VALID_DATE_AREA_MESSAGE);
        }

        return new PostFilterRequest(keyword, parsedStartDate, parsedEndDate);
    }

}
