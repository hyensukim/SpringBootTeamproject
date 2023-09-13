package com.springboot.shootformoney.member.validators;


import com.springboot.shootformoney.member.dto.SignUpForm;
import com.springboot.shootformoney.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class SignUpValidator implements Validator, PhoneFormValidator, PwFormValidator {

    private final MemberRepository memberRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return SignUpForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        /*
        - Id 중복 체크
        - 닉네임 중복 체크
        - 전화 번호 중복 체크
        - 이메일 중복 체크
        - 비밀번호 복잡도 맞는지 여부 체크(인터페이스)
        - 휴대번호 양식 체크(인터페이스)
         */
        SignUpForm signUpForm = (SignUpForm)target;

        String mId = signUpForm.getMId();
        String mNickName = signUpForm.getMNickName();
        String mEmail = signUpForm.getMEmail();
        String mPw = signUpForm.getMPassword();
        String mPwCheck = signUpForm.getMPasswordCheck();
        String mName = signUpForm.getMName();
        String mPhone = signUpForm.getMPhone();

        // 아이디 중복 체크
        if(mId != null && !mId.isBlank() && memberRepository.findBymId(mId) != null){
            errors.rejectValue("mId","","아이디 중복");
        }

        // 닉네임 중복 체크
        if(mNickName != null && !mNickName.isBlank() && memberRepository.findBymNickName(mNickName) != null){
            errors.rejectValue("mNickName","","닉네임 중복");
        }

        // 이메일 중복 체크
        if(mEmail != null && !mEmail.isBlank() && memberRepository.findBymEmail(mEmail) != null){
            errors.rejectValue("mEmail","","이메일 중복");
        }

        // 전화 번호 양식 확인 및 중복 체크
        if(mPhone != null && !mPhone.isBlank() && checkForm(mPhone)){
            if(memberRepository.findBymPhone(mPhone) != null){
                errors.rejectValue("mPhone","","전화 번호 중복");
            }else{
                mPhone = mPhone.replaceAll("\\D", "");
                signUpForm.setMPhone(mPhone);
            }
        }else{
            errors.rejectValue("mPhone","","전화 번호 양식이 잘못되었습니다.");
        }

        // 비밀번호 양식 체크
        if( mPw != null && !mPw.isBlank() && mPwCheck != null && !mPwCheck.isBlank()){
            // 비밀번호와 비밀번호 확인 일치여부 확인
            if(!mPw.equals(mPwCheck)){
                errors.rejectValue("mPasswordCheck","","비밀번호가 일치하지 않습니다.");
            }

            // 비밀번호 양식 확인
            if(!checkForm_number(mPw) || !checkForm_alphabet(mPw) || !checkForm_special(mPw)){
                errors.rejectValue("mPassword","","비밀번호 양식이 잘못되었습니다.");
            }
        }
    }
}
