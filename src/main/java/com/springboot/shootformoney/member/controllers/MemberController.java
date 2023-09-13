package com.springboot.shootformoney.member.controllers;

import com.springboot.shootformoney.member.dto.LoginForm;
import com.springboot.shootformoney.member.dto.SignUpForm;
import com.springboot.shootformoney.member.entity.Member;
import com.springboot.shootformoney.member.services.MemberSaveService;
import com.springboot.shootformoney.member.validators.SignUpValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberSaveService memberSaveService;
    private final SignUpValidator signUpValidator;

    @GetMapping("/signup")
    public String signUp(Model model){
        model.addAttribute("signUpForm",new SignUpForm());
        return "member/signup";
    }

    @PostMapping("/signup")
    public String signUpPs(Model model, @Valid SignUpForm signUpForm, Errors errors){

        signUpValidator.validate(signUpForm,errors);

        if(errors.hasErrors()){
            List<FieldError> list = errors.getFieldErrors();
            StringBuilder sb = new StringBuilder();
            for(FieldError f : list) sb.append(f.toString()).append("\n");

            System.out.println(sb);
            return "member/signUp";
        }

        memberSaveService.save(signUpForm);

        model.addAttribute("signUpForm",signUpForm);

        return "index";
    }

    @GetMapping("/login")
    public String login(@ModelAttribute LoginForm loginForm) {
        return "member/login";
    }

}
