package com.grasshouse.dorandoran.config.jwt;

import com.grasshouse.dorandoran.common.exception.InvalidAuthenticationException;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class BearerAuthInterceptor implements HandlerInterceptor {

    private static final String ALLOW_HTTP_METHOD = "GET";

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
        if (request.getMethod().equals(ALLOW_HTTP_METHOD) && !requestUrlContains(request, "/my")) {
            return true;
        }

        String token = authExtractor.extract(request, "Bearer");

        String id = Optional.ofNullable(token)
            .filter(t -> jwtTokenProvider.validateToken(t))
            .map(t -> jwtTokenProvider.getSubject(t))
            .orElseThrow(InvalidAuthenticationException::new);

        request.setAttribute("id", id);
        return true;
    }

    private boolean requestUrlContains(HttpServletRequest request, String regex) {
        return request.getRequestURL()
            .toString()
            .contains(regex);
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
