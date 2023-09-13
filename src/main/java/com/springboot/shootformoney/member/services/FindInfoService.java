package com.springboot.shootformoney.member.services;

import com.springboot.shootformoney.member.dto.FindIdForm;
import com.springboot.shootformoney.member.entity.Member;
import com.springboot.shootformoney.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
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
     * 값이 있으면, 해당 회원의 회원 번호 반환.
     * @param mId
     * @return
     */
    public Long findPassword(String mId, String mEmail){
        Member member = memberRepository.findBymIdAndmEmail(mId,mEmail);

        if(member == null){return null;}

        return member.getMNo();
    }
}
