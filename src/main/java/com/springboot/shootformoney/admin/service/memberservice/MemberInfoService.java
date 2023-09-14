package com.springboot.shootformoney.admin.service.memberservice;

import com.springboot.shootformoney.member.entity.Member;
import com.springboot.shootformoney.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/*
 관리자 - 회원 조회 서비스
 */

@Service
public class MemberInfoService {

    private MemberRepository memberRepository;

    @Autowired
    public MemberInfoService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    // 회원 전체 조회
    public List<Member> findAllMembers() {
        return memberRepository.findAll();
    }

    // 회원 단일 조회 (id)
    public Member findMember (String mId){
        Member member = memberRepository.findBymId(mId);
        if(member != null) {
            return member;
        } else {
            throw new IllegalArgumentException("존재하지 않는 회원입니다.");
        }
    }
}
