package com.grasshouse.dorandoran.config.jwt;

import com.grasshouse.dorandoran.common.exception.InvalidAuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class BearerAuthInterceptor implements HandlerInterceptor {

    private AuthorizationExtractor authExtractor;
    private JwtTokenProvider jwtTokenProvider;

    public BearerAuthInterceptor(AuthorizationExtractor authExtractor,
        JwtTokenProvider jwtTokenProvider) {
        this.authExtractor = authExtractor;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
        Object handler) {
        String token = authExtractor.extract(request, "Bearer");

        if (!jwtTokenProvider.validateToken(token)) {
            throw new InvalidAuthenticationException("올바르지 않은 토큰 입니다.");
        }
        String id = jwtTokenProvider.getSubject(token);
        request.setAttribute("id", id);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
        ModelAndView modelAndView) {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
        Object handler, Exception ex) {

    }
}
