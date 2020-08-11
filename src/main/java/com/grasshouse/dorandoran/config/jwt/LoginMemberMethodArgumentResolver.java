package com.grasshouse.dorandoran.config.jwt;

import static org.springframework.web.context.request.RequestAttributes.SCOPE_REQUEST;

import com.grasshouse.dorandoran.common.exception.InvalidAuthenticationException;
import com.grasshouse.dorandoran.member.repository.MemberRepository;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
public class LoginMemberMethodArgumentResolver implements HandlerMethodArgumentResolver {

    private final MemberRepository memberRepository;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(LoginMember.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
        NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        String id = (String) webRequest.getAttribute("id", SCOPE_REQUEST);
        if (Objects.isNull(id)) {
            throw new InvalidAuthenticationException("비정상적인 접근");
        }
        try {
            return memberRepository.findByoAuthId(id);
        } catch (Exception e) {
            throw new InvalidAuthenticationException("비정상적인 로그인");
        }
    }
}
