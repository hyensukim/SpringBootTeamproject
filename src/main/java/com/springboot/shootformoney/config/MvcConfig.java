package com.springboot.shootformoney.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
@EnableJpaAuditing // Test 시, 사용하는 설정과 구분하기 위해 별도의 설정 클래스에 지정
public class MvcConfig implements WebMvcConfigurer {

    // 사이트 설정 유지 인터셉터
    private final SiteConfigInterceptor siteConfigInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(siteConfigInterceptor)
                .addPathPatterns("/**");
    }

    @Bean
    public HiddenHttpMethodFilter httpMethodFilter() {  // GET, POST외에 DELETE, PATCH, PUT ....


        return new HiddenHttpMethodFilter();
    }
}
