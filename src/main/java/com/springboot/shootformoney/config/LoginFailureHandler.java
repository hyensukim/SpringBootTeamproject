//package com.springboot.shootformoney.config;
//
//import com.springboot.shootformoney.member.exceptions.LoginValidationException;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.authentication.AuthenticationFailureHandler;
//
//import java.io.IOException;
//
//public class LoginFailureHandler implements AuthenticationFailureHandler {
//    @Override
//    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
//        HttpSession session = request.getSession();
//        String mId = request.getParameter("mId");
//        String mPassword = request.getParameter("mPassword");
//
//        session.removeAttribute("mId");
//        session.removeAttribute("requiredMId");
//        session.removeAttribute("requiredMPassword");
//        session.removeAttribute("loginGlobal");
//
//        session.setAttribute("mId",mId);
//
//        try{
//            if(mId == null || mId.isBlank()){
//                throw new LoginValidationException("아이디를 입력하세요","requiredMId");
//            }
//            if(mPassword == null || mPassword.isBlank()){
//                throw new LoginValidationException("비밀번호를 입력하세요.","requiredMPassword");
//            }
//            throw new LoginValidationException("로그인에 실패하였습니다.","loginGlobal");
//
//        }catch(LoginValidationException e){
//            session.setAttribute(e.getField(),e.getMessage());
//        }
//
//        String url = request.getContextPath() + "/member/login";
//        response.sendRedirect(url);
//
//    }
//}
