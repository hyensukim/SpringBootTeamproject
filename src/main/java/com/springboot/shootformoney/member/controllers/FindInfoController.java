package com.springboot.shootformoney.member.controllers;

import com.springboot.shootformoney.member.dto.FindIdForm;
import com.springboot.shootformoney.member.dto.FindPwForm;
import com.springboot.shootformoney.member.entity.Member;
import com.springboot.shootformoney.member.services.FindInfoService;
import com.springboot.shootformoney.member.services.MemberUpdateService;
import com.springboot.shootformoney.member.services.SendEmailService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class FindInfoController {

    private final FindInfoService findInfoService;
    private final SendEmailService sendEmailService;
    private final MemberUpdateService updateService;
    
    // 회원 아이디 찾기
    @GetMapping("/findid")
    public String findId(Model model){
        model.addAttribute("findIdForm",new FindIdForm());
        return "member/findid";
    }

    @PostMapping("/findid")
    public String findIdPs(@Valid FindIdForm findIdForm, Errors errors, Model model){
        String mName = findIdForm.getMName();
        String mEmail = findIdForm.getMEmail();
        String script = "";
        if(mName != null && !mName.isBlank() && mEmail != null && !mEmail.isBlank()){
            String findId = findInfoService.findId(findIdForm);
            if(findId != null) {
                script = String.format("Swal.fire('회원 아이디 : ','<h2>%s</h2>','success')" +
                        ".then(function(){location.href='/member/login';})",findId);
                model.addAttribute("script", script);
                return "script/sweet";
            }
            script = String.format("Swal.fire('등록 되지 않은 회원입니다.','','error')" +
                    ".then(function(){location.href='/member/findid';})");
            model.addAttribute("script", script);
            return "script/sweet";
        }
        model.addAttribute("findIdForm",findIdForm);
        return "member/findid";

    }

    // 회원 비밀번호 찾기
    @GetMapping("/findpw")
    public String findPw(Model model){
        model.addAttribute("findPwForm",new FindPwForm());
        return "member/findpw";
    }

    @PostMapping("/findpw")
    public String findPwPs(@Valid FindPwForm findPwForm,Errors errors,Model model){

        String mId = findPwForm.getMId();
        String mEmail = findPwForm.getMEmail();
        String script = "";

        if(mId != null && !mId.isBlank() && mEmail != null && !mEmail.isBlank()){
            Member member = findInfoService.findPw(findPwForm);
            if(member != null){
                try {
                    sendEmailService.sendResetPwUrlByEmail(member);
                    updateService.updatePw(member.getMNo(), sendEmailService.getTempPw());
                    script = String.format("Swal.fire('\"임시 비밀번호\" 관련 이메일이 전송되었습니다.')" +
                            ".then(function(){location.href='/member/login';})");
                    model.addAttribute("script",script);
                    return "script/sweet";
                } catch (MessagingException e) {
                    errors.reject("","이메일 전송에 실패하였습니다.");
                    return "member/findpw";
                }
            }
            script = String.format("Swal.fire('등록 되지 않은 회원입니다.','','error')" +
                    ".then(function(){history.back();})");
            model.addAttribute("script", script);
            return "script/sweet";
        }

        model.addAttribute("findPwForm",findPwForm);
        return "member/findpw";
    }
}
