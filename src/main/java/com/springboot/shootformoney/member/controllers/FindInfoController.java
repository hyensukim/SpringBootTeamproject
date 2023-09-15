package com.springboot.shootformoney.member.controllers;

import com.springboot.shootformoney.member.dto.FindIdForm;
import com.springboot.shootformoney.member.dto.FindPwForm;
import com.springboot.shootformoney.member.dto.SignUpForm;
import com.springboot.shootformoney.member.entity.Member;
import com.springboot.shootformoney.member.services.FindInfoService;
import com.springboot.shootformoney.member.services.MemberUpdateService;
import com.springboot.shootformoney.member.services.SendEmailService;
import com.springboot.shootformoney.member.utils.MemberUtil;
import com.springboot.shootformoney.member.validators.SignUpValidator;
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
    private final MemberUtil memberUtil;
    private final SignUpValidator signUpValidator;
    
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
                    ".then(function(){location.href='/member/findpw';})");
            model.addAttribute("script", script);
            return "script/sweet";
        }

        model.addAttribute("findPwForm",findPwForm);
        return "member/findpw";
    }

    // 비밀번호 변경
    @GetMapping("/resetpw")
    public String resetPw(Model model) {
        model.addAttribute("signUpForm",new SignUpForm());
        return "member/checkpw";
    }

    // 비밀번호 변경 전 확인
    @PostMapping("/checkpw")
    public String checkPw(@ModelAttribute @Valid SignUpForm signUpForm,Errors errors,Model model){
        Member member = memberUtil.getEntity();
        String mPw = member.getMPassword();
        Long mNo = member.getMNo();

        String inputPassword = signUpForm.getMPassword();

        String script = "";
        if(inputPassword == null){
            script = String.format("Swal.fire('비밀번호를 입력해주세요.')" +
                    ".then(function(){forward.href='/member/checkpw';})");
            model.addAttribute("script",script);
            return "script/sweet";
        }

        if(inputPassword.equals(mPw)){
            return "redirect:/member/resetpw/" + mNo;
        }
        errors.reject("","비밀번호가 일치하지 않습니다.");
        return "member/checkpw";
    }

    @PostMapping("/resetpw/{no}")
    public String resetPwPs(@PathVariable Long no, @ModelAttribute SignUpForm signUpForm
            , Errors errors, Model model) {
        String pw = signUpForm.getMPassword();
        try {
            signUpValidator.validate(signUpForm, errors);
            if (errors.hasErrors()) {
                return "member/resetpw/"+no;
            }
            updateService.updatePw(no, pw);
        } catch (Exception e) {

            return "member/findpw/"+no;
        }
        String script = String.format("Swal.fire('수정 완료!', '', 'success')" +
                ".then(function() {location.href='/member/login';})");
        model.addAttribute("script", script);
        return "script/sweet";
    }

}
