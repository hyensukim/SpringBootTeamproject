package com.springboot.shootformoney.config;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;

import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        /**
         * 로그인 및 로그아웃 처리
         */
        try {
            http.csrf(csrf -> csrf.ignoringRequestMatchers(
                            "/v3/api-docs/**",
                            "/swagger-ui/**",
                            "/swagger-resources/**"))
                    .cors(withDefaults())
                    .formLogin(f -> f
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
                                session.invalidate();//세션 삭제
                            })//로그아웃 처리
                            .logoutSuccessHandler((req, resp, authentication) -> {
                                resp.sendRedirect("/member/login");
                            })//로그아웃 성공 후 처리
                    );
        }catch(Exception e){
            e.printStackTrace();
        }

        /**
         * 권한 설정
         */
        http.authorizeHttpRequests(f->f
                .requestMatchers("/mypage/**").authenticated() // 회원 전용
//                .requestMatchers("/admin/**").hasAnyAuthority("ADMIN")// 관리자 전용\
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

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
