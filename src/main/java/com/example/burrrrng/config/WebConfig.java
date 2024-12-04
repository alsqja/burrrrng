package com.example.burrrrng.config;

import com.example.burrrrng.config.interceptor.AuthInterceptor;
import com.example.burrrrng.config.interceptor.OwnerRoleInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private static final String[] AUTH_REQUIRED_PATH_PATTERNS = {"/**"};
    private static final String[] AUTH_EXCLUDE_PATH_PATTERNS = {"/users/login", "/users/signup"};
    private static final String[] ADMIN_ROLE_REQUIRED_PATH_PATTERNS = {"/owner/*"};

    private final AuthInterceptor authInterceptor;
    private final OwnerRoleInterceptor ownerRoleInterceptor;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor)
                .addPathPatterns(AUTH_REQUIRED_PATH_PATTERNS)
                .excludePathPatterns(AUTH_EXCLUDE_PATH_PATTERNS)
                .order(Ordered.HIGHEST_PRECEDENCE);

        registry.addInterceptor(ownerRoleInterceptor)
                .addPathPatterns(ADMIN_ROLE_REQUIRED_PATH_PATTERNS)
                .order(Ordered.HIGHEST_PRECEDENCE + 1);

    }
}
