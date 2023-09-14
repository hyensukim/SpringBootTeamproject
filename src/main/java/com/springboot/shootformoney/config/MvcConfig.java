package com.springboot.shootformoney.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableJpaAuditing // Test 시, 사용하는 설정과 구분하기 위해 별도의 설정 클래스에 지정
public class MvcConfig implements WebMvcConfigurer {

}
