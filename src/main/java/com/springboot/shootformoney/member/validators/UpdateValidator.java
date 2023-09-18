package com.springboot.shootformoney.member.validators;

import com.springboot.shootformoney.member.dto.SignUpForm;
import com.springboot.shootformoney.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class UpdateValidator implements Validator, PhoneFormValidator, PwFormValidator {

    private final MemberRepository memberRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return SignUpForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        SignUpForm signUpForm = (SignUpForm) target;
        String updatePw = signUpForm.getMPassword();
        String updatePwCheck = signUpForm.getMPasswordCheck();
        String mPhone = signUpForm.getMPhone();

        // 비밀번호 양식 체크
        if( updatePw != null && !updatePw.isBlank()){
            // 비밀번호 양식 확인
            if(!checkForm_number(updatePw) || !checkForm_alphabet(updatePw) || !checkForm_special(updatePw)){
                errors.rejectValue("mPassword","","비밀번호 양식이 잘못되었습니다.");
            }
        }

        //비밀번호 일치 여부
        if(updatePw != null && !updatePw.isBlank() && updatePwCheck != null && !updatePwCheck.isBlank()
                && !updatePw.equals(updatePwCheck)){
            errors.rejectValue("mPasswordCheck","","비밀번호가 일치하지 않습니다.");
        }

        // 휴대전화 양식 확인
        if(mPhone != null && !mPhone.isBlank()){
            if(!checkMobile(mPhone)){
                errors.rejectValue("mPhone","","전화번호 양식이 잘못되었습니다.");
            }
            mPhone = mPhone.replaceAll("\\D","");
            signUpForm.setMPhone(mPhone);
        }
    }
}
