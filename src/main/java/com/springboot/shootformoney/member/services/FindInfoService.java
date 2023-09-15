package com.springboot.shootformoney.member.services;

import com.springboot.shootformoney.member.dto.FindIdForm;
import com.springboot.shootformoney.member.dto.FindPwForm;
import com.springboot.shootformoney.member.entity.Member;
import com.springboot.shootformoney.member.repository.MemberRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindInfoService {

    private final MemberRepository memberRepository;

    public String findId(FindIdForm findIdForm){
        String mName = findIdForm.getMName();
        String mEmail = findIdForm.getMEmail();

        Member member = memberRepository.findBymEmailAndmName(mEmail,mName);

        if(member == null) return null;

        return member.getMId();
    }

    /**
     * 입력 폼에서 아이디 및 이메일을 입력.
     * 이메일과 아이디로 조회.
     * 값이 있으면, 이메일로 회원 비밀번호 변경 페이지로 이동하는 url 전송.
     * 없으면, 없는 회원이라는 js 띄우기
     * url -> 새로운 비밀번호/ 비밀번호 확인 입력 -> 로그인 페이지 이동.
     */
    public Member findPw(FindPwForm findPwForm) {
        String mId = findPwForm.getMId();
        String mEmail = findPwForm.getMEmail();

        return memberRepository.findBymIdAndmEmail(mId,mEmail);
    }
}
