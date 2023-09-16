package com.springboot.shootformoney.member.services;

import com.springboot.shootformoney.member.entity.Member;
import com.springboot.shootformoney.member.exceptions.MemberNotExistException;
import com.springboot.shootformoney.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberDeleteService {

    private final MemberRepository memberRepository;

    public void delete(Long mNo){
        Member member = memberRepository.findById(mNo).orElseThrow(MemberNotExistException::new);
        memberRepository.delete(member);
        memberRepository.flush();
    }
}
