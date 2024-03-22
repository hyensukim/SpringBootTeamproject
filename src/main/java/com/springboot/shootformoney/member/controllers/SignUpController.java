package com.springboot.shootformoney.member.controllers;

import com.springboot.shootformoney.member.dto.SignUpForm;
import com.springboot.shootformoney.member.services.MemberSaveService;
import com.springboot.shootformoney.member.validators.SignUpValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class SignUpController {
    private final MemberSaveService memberSaveService;
    private final SignUpValidator signUpValidator;

    @GetMapping("/signup")
    public String signUp(Model model){
        model.addAttribute("pageTitle","회원가입");
        model.addAttribute("signUpForm",new SignUpForm());
        return "member/signup";
    }

    @PostMapping("/signup")
    public String signUpPs(Model model, @Valid SignUpForm signUpForm, Errors errors){

        signUpValidator.validate(signUpForm,errors);

        if(errors.hasErrors()) return "member/signup";

        memberSaveService.save(signUpForm);

        model.addAttribute("signUpForm",signUpForm);

        return "redirect:/member/login";
    }

//    @GetMapping("/checkMemberId")
//    @ResponseBody
//    public Map<String,Boolean> checkMemberId(@RequestParam("mId") String mId){
//        boolean isDuplicate = memberSaveService.checkDupId(mId);
//        Map<String,Boolean> response = new HashMap<>();
//        response.put("isDuplicate",isDuplicate);
//        return response;
//    }
}
