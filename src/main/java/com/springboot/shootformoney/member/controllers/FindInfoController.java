package com.springboot.shootformoney.member.controllers;

import com.springboot.shootformoney.member.dto.FindIdForm;
import com.springboot.shootformoney.member.services.FindInfoService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
public class FindInfoController {

    private FindInfoService findInfoService;

    @GetMapping("/findid")
    public String findId(Model model){
        model.addAttribute("findIdForm",new FindIdForm());
        return "member/findid";
    }

//    @PostMapping("/findid")
//    public String findIdPs(@Valid FindIdForm findIdForm, Errors errors, Model model){
//        String findId = findInfoService.findId(findIdForm);
//        if(findId == null){
//            errors.reject("","등록되지 않은 회원입니다.");
//        }else{
//
//        }
//    }
}
