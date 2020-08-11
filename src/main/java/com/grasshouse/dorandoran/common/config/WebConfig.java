package com.grasshouse.dorandoran.common.config;

import com.grasshouse.dorandoran.post.controller.PostSearchArgumentResolver;
import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final PostSearchArgumentResolver postSearchArgumentResolver;

    public WebConfig(PostSearchArgumentResolver postSearchArgumentResolver) {
        this.postSearchArgumentResolver = postSearchArgumentResolver;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(postSearchArgumentResolver);
    }
}
