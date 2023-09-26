package com.springboot.shootformoney.member.controllers;

import com.springboot.shootformoney.member.dto.LoginForm;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class LoginController {
    @GetMapping("/login")
    public String login(HttpSession httpSession,
                        @CookieValue(required = false) String saveId, Model model){
        model.addAttribute("pageTitle","로그인");
        LoginForm loginForm = new LoginForm();
        if(saveId != null){
            loginForm.setMId(saveId);
            loginForm.setSaveId(true);
        }

        String mId = (String) httpSession.getAttribute("mId");
        if(mId != null){
            loginForm.setMId(mId);
        }

        model.addAttribute("loginForm",loginForm);
        return "member/login";
    }

}
