package com.grasshouse.dorandoran.common.config;

import com.grasshouse.dorandoran.post.controller.PostFilterArgumentResolver;
import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final PostFilterArgumentResolver PostFilterArgumentResolver;

    public WebConfig(PostFilterArgumentResolver PostFilterArgumentResolver) {
        this.PostFilterArgumentResolver = PostFilterArgumentResolver;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(PostFilterArgumentResolver);
    }
}
