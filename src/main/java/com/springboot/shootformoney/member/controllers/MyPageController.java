package com.springboot.shootformoney.member.controllers;

import com.springboot.shootformoney.member.dto.MemberInfo;
import com.springboot.shootformoney.member.entity.Member;
import com.springboot.shootformoney.member.services.MemberPwCheckService;
import com.springboot.shootformoney.member.utils.MemberUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/member/mypage")
@RequiredArgsConstructor
public class MyPageController {

    private final MemberUtil memberUtil;
    private final MemberPwCheckService memberPwCheckService;

    // 비밀번호 확인
    @GetMapping("/checkpw")
    public String checkPw(Model model){
        model.addAttribute("memberInfo",new MemberInfo());
        return "member/checkpw";
    }

    @PostMapping("/checkpw")
    public String checkPw(MemberInfo memberInfo, Model model) {
        String mPassword = memberInfo.getMPassword();
        MemberInfo getMember = memberUtil.getMember();

        if(getMember == null){
            return "redirect:/member/login";
        }

        Long mNo = getMember.getMNo();

        try {
            memberPwCheckService.check(mPassword);
            return "redirect:/member/mypage/info/" + mNo;
        } catch (Exception e) {
            String script = String.format("Swal.fire('%s', '', 'error').then(function(){history.back();})", e.getMessage());
            model.addAttribute("script", script);
            return "script/sweet";
        }
    }

    @GetMapping("/info/{no}")
    public String myPage(@PathVariable Long mNo, Model model){

        return "member/mypage";
    }

    //    @PostMapping("/resetpw/{no}")
//    public String resetPwPs(@PathVariable Long no, @ModelAttribute SignUpForm signUpForm
//            , Errors errors, Model model) {
//        String pw = signUpForm.getMPassword();
//        try {
//            signUpValidator.validate(signUpForm, errors);
//            if (errors.hasErrors()) {
//                return "member/resetpw/"+no;
//            }
//            updateService.updatePw(no, pw);
//        } catch (Exception e) {
//
//            return "member/findpw/"+no;
//        }
//        String script = String.format("Swal.fire('수정 완료!', '', 'success')" +
//                ".then(function() {location.href='/member/login';})");
//        model.addAttribute("script", script);
//        return "script/sweet";
//    }
}
