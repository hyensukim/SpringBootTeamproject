package com.springboot.shootformoney.config;

import com.springboot.shootformoney.member.dto.MemberInfo;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    /**
     * authentication : 회원 인증(회원 가입한 회원인지 확인)
     * 로그인 회원 세션에 저장
     * cookie를 이용한 아이디 저장 기능 구현
     * @param request the request which caused the successful authentication
     * @param response the response
     * @param authentication the <tt>Authentication</tt> object which was created during
     * the authentication process.
     * @throws IOException
     * @throws ServletException
     */

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        HttpSession session = request.getSession();
        session.removeAttribute("mId");
        session.removeAttribute("requiredMId");
        session.removeAttribute("requiredMPassword");
        session.removeAttribute("loginGlobal");

        MemberInfo memberInfo = (MemberInfo)authentication.getPrincipal(); // getPrincipal() UserDetails를 구현한 객체 반환.
        session.setAttribute("memberInfo",memberInfo); // 로그인한 회원 정보 세션에 저장.

        // 쿠키 기능 구현 - S
        Cookie cookie = new Cookie("saveId",request.getParameter("mId"));
        if(request.getParameter("saveId") == null){
            cookie.setMaxAge(0); // 쿠키 삭제
        }else{
            cookie.setMaxAge(60 * 60 * 24 * 30);
        }
        response.addCookie(cookie);
        // 쿠키 기능 구현 - E

        String url = request.getContextPath() + "/";
        response.sendRedirect(url); // 로그인 성공 시, 메인 페이지로 이동
    }
}
