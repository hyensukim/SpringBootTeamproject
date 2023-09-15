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

    public void updatePw(long memberNo, String memberPw){
        Member member = memberRepository.findById(memberNo).get();

        member.setMPassword(passwordEncoder.encode(memberPw));

        memberRepository.saveAndFlush(member);
    }
}
