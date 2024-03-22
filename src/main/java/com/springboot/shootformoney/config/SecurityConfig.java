package com.springboot.shootformoney.config;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        /**
         * 로그인 및 로그아웃 처리
         */

        http.formLogin(f -> f
                .loginPage("/member/login")
                .usernameParameter("mId")
                .passwordParameter("mPassword")
                .successHandler(new LoginSuccessHandler())
                .failureHandler(new LoginFailureHandler())
            ).logout(f -> f
                .logoutRequestMatcher(new AntPathRequestMatcher("/member/logout")) // 로그아웃 처리 url
                .logoutSuccessUrl("/member/login")// 로그아웃 성공 후 이동 페이지
                .deleteCookies("JSESSIONID", "saveId")// 로그아웃 이후 쿠키 삭제
                .addLogoutHandler((req, resp, authentication) -> {
                    HttpSession session = req.getSession();
                    session.invalidate();})//세션 삭제
                .logoutSuccessHandler((req, resp, authentication) -> {
                    resp.sendRedirect("/member/login");
                })
            );

        /**
         * 권한 설정
         */
        http.authorizeHttpRequests(f->f
            .requestMatchers("/member/login").anonymous()
            .requestMatchers("/member/mypage/**").authenticated() // 회원 전용
            .requestMatchers("/post/**").authenticated()
            .requestMatchers("/list/unstarted/**").authenticated()
            .requestMatchers("/admin/**").hasAnyAuthority("ADMIN")// 관리자 전용\
            .anyRequest().permitAll()
        );

        /**
         * 인증 관련 예외 처리
         */
        http.exceptionHandling(f->f
                .authenticationEntryPoint((req,resp,e)->{
                    String uri =req.getRequestURI();
                    if(uri.contains("/admin")){
                        resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "NOT AUTHORIZED");
                    }else{
                        String redirectUrl =req.getContextPath() + "/member/login";
                        resp.sendRedirect(redirectUrl);
                    }
                })
        );
        
        /**
         * csrf 관련 처리
         */
        http.csrf(csrf->csrf
                .ignoringRequestMatchers(new AntPathRequestMatcher("/member/resetpw/**"))
                .ignoringRequestMatchers(new AntPathRequestMatcher("/admin/member/**"))
                .ignoringRequestMatchers(new AntPathRequestMatcher("/member/mypage/**"))
        );

        http.headers(f->f.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}

