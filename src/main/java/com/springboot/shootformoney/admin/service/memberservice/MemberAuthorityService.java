package com.springboot.shootformoney.admin.service.memberservice;

import com.springboot.shootformoney.member.entity.Member;
import com.springboot.shootformoney.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/*
 관리자 - 회원 제재 서비스
 */

@Service
@RequiredArgsConstructor
public class MemberAuthorityService {

    private final MemberRepository memberRepository;

    // 회원 삭제
    @Transactional
    public void deleteMember(String mId){
        Member member = memberRepository.findBymId(mId);
        if(member != null){
            memberRepository.delete(member);
        } else {
            throw new IllegalArgumentException("존재하지 않는 회원입니다.");
        }
    }
}
