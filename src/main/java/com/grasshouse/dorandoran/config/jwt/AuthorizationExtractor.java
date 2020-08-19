package com.grasshouse.dorandoran.config.jwt;

import java.util.Enumeration;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

@Component
public class AuthorizationExtractor {

    public static final String AUTHORIZATION = "Authorization";
    public static final String ACCESS_TOKEN_TYPE =
        AuthorizationExtractor.class.getSimpleName() + ".ACCESS_TOKEN_TYPE";

    public String extract(HttpServletRequest request, String type) {
        Enumeration<String> headers = request.getHeaders(AUTHORIZATION);
        while (headers.hasMoreElements()) {
            String value = headers.nextElement();
            if ((value.toLowerCase().startsWith(type.toLowerCase()))) {
                String authHeaderValue = value.substring(type.length()).trim();
                request.setAttribute(ACCESS_TOKEN_TYPE, value.substring(0, type.length()).trim());
                int commaIndex = authHeaderValue.indexOf(',');
                if (commaIndex > 0) {
                    authHeaderValue = authHeaderValue.substring(0, commaIndex);
                }
                return authHeaderValue;
            }
        }

        return Optional.ofNullable(request.getParameter("token"))
            .orElse(Strings.EMPTY);
    }
}
