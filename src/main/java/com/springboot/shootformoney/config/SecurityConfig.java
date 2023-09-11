package com.springboot.shootformoney.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.formLogin(f->f
                .loginPage("member/login")
                .usernameParameter("m_id")
                .passwordParameter("m_password")
                .defaultSuccessUrl("/")
        );

        return http.build();
    }

}
