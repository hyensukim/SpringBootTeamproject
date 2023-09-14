package com.springboot.shootformoney.member.controllers;

import com.springboot.shootformoney.member.dto.FindIdForm;
import com.springboot.shootformoney.member.services.FindInfoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class FindInfoController {

    private final FindInfoService findInfoService;

    @GetMapping("/findid")
    public String findId(Model model){
        model.addAttribute("findIdForm",new FindIdForm());
        return "member/findid";
    }

    @PostMapping("/findid")
    public String findIdPs(@Valid FindIdForm findIdForm, Errors errors, Model model){
        String mName = findIdForm.getMName();
        String mEmail = findIdForm.getMEmail();
        String findId = findInfoService.findId(findIdForm);
        System.out.println(mName +  mEmail + findId);
        if(!mName.isBlank() && !mEmail.isBlank()){
            if(findId != null) {
                String script = String.format("Swal.fire('회원 아이디 : [" + findId
                        + "] 입니다.').then(function(){location.href='/member/login';})");
                model.addAttribute("script", script);
                return "script/sweet";
            }
            errors.reject("","등록되지 않은 회원입니다.");
        }
        return "member/findid";

    }
}
