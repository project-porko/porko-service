package io.porko.pagination.config;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    public static final int MAX_PAGE_SIZE = 100;
    private final PaginationArgumentResolver paginationArgumentResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        paginationArgumentResolver.setMaxPageSize(MAX_PAGE_SIZE);
        resolvers.add(paginationArgumentResolver);
    }
}
