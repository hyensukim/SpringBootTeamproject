package com.springboot.shootformoney.config;

import com.springboot.shootformoney.common.interceptors.MemberUpdateInterceptor;
import com.springboot.shootformoney.common.interceptors.ScoreUpdateInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
@EnableJpaAuditing // Test 시, 사용하는 설정과 구분하기 위해 별도의 설정 클래스에 지정
public class MvcConfig implements WebMvcConfigurer {

    private final MemberUpdateInterceptor memberUpdateInterceptor;
    private final ScoreUpdateInterceptor scoreUpdateInterceptor;
    private final SiteConfigInterceptor siteConfigInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(siteConfigInterceptor)
                .addPathPatterns("/**");
        registry.addInterceptor(memberUpdateInterceptor)
                .addPathPatterns("/**");
        registry.addInterceptor(scoreUpdateInterceptor)
                .addPathPatterns("/list/**");

    }
}
