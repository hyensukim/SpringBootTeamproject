package com.springboot.shootformoney.member.controllers;

import com.springboot.shootformoney.member.dto.MemberInfo;
import com.springboot.shootformoney.member.dto.SignUpForm;
import com.springboot.shootformoney.member.services.MemberDeleteService;
import com.springboot.shootformoney.member.services.MemberPwCheckService;
import com.springboot.shootformoney.member.services.MemberUpdateService;
import com.springboot.shootformoney.member.utils.MemberUtil;
import com.springboot.shootformoney.member.validators.SignUpValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/member/mypage")
@RequiredArgsConstructor
public class MyPageController {

    private final MemberUtil memberUtil;
    private final MemberPwCheckService memberPwCheckService;
    private final MemberUpdateService memberUpdateService;
    private final SignUpValidator signUpValidator;
    private final MemberDeleteService memberDeleteService;

    // 마이페이지 들어가기 전 비밀번호 확인
    @GetMapping("/checkpw")
    public String checkPw(Model model){
        model.addAttribute("memberInfo",new MemberInfo());
        return "member/mypage/checkpw";
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

    // 마이페이지 - 회원 정보
    @GetMapping("/info/{mNo}")
    public String myPage(@PathVariable Long mNo, Model model){

        // 다른 회원이 마이페이지에 접근하는 것을 방지
        if(memberUtil.isLogin()){
            Long no = memberUtil.getMember().getMNo();
            if(!mNo.equals(no)){
                String script = String.format("Swal.fire('본인 계정만 접근 가능합니다.', '', 'error')" +
                        ".then(function(){location.href='/';})");
                model.addAttribute("script",script);
                return "script/sweet";
            }
        }

        MemberInfo memberInfo = memberUtil.getMember();
        SignUpForm signUpForm = SignUpForm.builder()
                .mId(memberInfo.getMId())
                .mName(memberInfo.getMName())
                .mNickName(memberInfo.getMNickName())
                .grade(memberInfo.getGrade())
                .level(memberInfo.getLevel())
                .mPhone(memberInfo.getMPhone())
                .mEmail(memberInfo.getMEmail())
                .build();
        model.addAttribute("signUpForm",new SignUpForm());
        return "member/mypage/info";
    }

    @PostMapping("/info/{mNo}")
    public String myPagePs(@PathVariable Long mNo, @ModelAttribute @Valid SignUpForm signUpForm,
                           Errors errors, Model model){

        signUpValidator.validate(signUpForm,errors);

        if(errors.hasErrors()){
            return "member/mypage/info";
        }

        String mPassword = signUpForm.getMPassword();
        String mPhone = signUpForm.getMPhone();
        String mEmail = signUpForm.getMEmail();
        memberUpdateService.update(mNo,mPassword,mPhone,mEmail);
        String script = String.format("Swal.fire('수정 완료, 재로그인 시 수정된 정보가 반영됩니다. :D','success')" +
                ".then(function(){history.back();})");
        model.addAttribute("script",script);
        return "script/sweet";
    }

    // 마이페이지 - 회원탈퇴
    @GetMapping("/delete/{mNo}")
    public String delete(@PathVariable Long mNo){
        memberDeleteService.delete(mNo);
        return "redirect:/member/logout";
    }

    @GetMapping("/deletepw/{mNo}")
    public String deleteconfirm(@PathVariable Long mNo, @ModelAttribute MemberInfo memberInfo, Model model) {
        return "member/mypage/checkpw";
    }

    @PostMapping("/deletepw/{mNo}")
    public String deleteconfirmPs(@PathVariable Long mNo, MemberInfo memberInfo, Model model) {
        String mPassword = memberInfo.getMPassword();
        String url = "/member/mypage/delete/" + mNo;
        try {
            if (memberPwCheckService.check(mPassword)) {
                model.addAttribute("confirmUrl", url);
                return "script/sweet";
            }
        } catch (Exception e) {
            String script = String.format("Swal.fire('%s', '', 'error').then(function(){history.back();})", e.getMessage());
            model.addAttribute("script", script);
            return "script/sweet";
        }
        return "redirect:/member/mypage/info/"+mNo;
    }
    
}
