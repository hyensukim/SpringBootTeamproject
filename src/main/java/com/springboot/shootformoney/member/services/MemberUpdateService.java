package com.springboot.shootformoney.member.services;

import com.springboot.shootformoney.member.entity.Member;
import com.springboot.shootformoney.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberUpdateService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public void update(long mNo, String mPassword, String mPhone){
        Member member = memberRepository.findById(mNo).get();

        if(mPassword != null && !mPassword.isBlank()) {
            member.setMPassword(passwordEncoder.encode(mPassword));
        }

        if(mPhone != null && !mPhone.isBlank()){
            mPhone = mPhone.replaceAll("\\D","");
            member.setMPhone(mPhone);
        }

        memberRepository.saveAndFlush(member);
    }

    public void updatePw(long mNo, String mPw){
        update(mNo,mPw,null);
    }
}
