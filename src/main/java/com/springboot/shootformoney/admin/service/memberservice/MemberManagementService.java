package com.springboot.shootformoney.admin.service.memberservice;

import com.springboot.shootformoney.member.entity.Member;
import com.springboot.shootformoney.member.enum_.Grade;
import com.springboot.shootformoney.member.enum_.Role;
import com.springboot.shootformoney.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/*
 관리자 - 회원 조회 서비스
 */

@Service
@RequiredArgsConstructor
public class MemberManagementService {

    private MemberRepository memberRepository;

    @Autowired
    public MemberManagementService(MemberRepository memberRepository) {
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

    // 회원 권한 변경
    @Transactional
    public void changeMemberRole(String mId, Role newRole) {
        Member member = memberRepository.findBymId(mId);
        if (member != null) { // ADMIN 계정 추가 이후에 수정 예정
            member.setRole(newRole);  //
            memberRepository.save(member);  // 변경된 정보 저장
        } else {
            throw new IllegalArgumentException("존재하지 않는 회원입니다.");
        }
    }

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

    public List<Member> searchMembers(String category, String query) {
        switch (category) {
            case "mNo":
                return memberRepository.findBymNo(Long.parseLong(query));

            case "mId":
                return Arrays.asList(memberRepository.findBymId(query));

            case "mName":
                return Arrays.asList(memberRepository.findBymName(query));

            case "mNickName":
                return Arrays.asList(memberRepository.findBymNickName(query));

            case "grade":
                return memberRepository.findByGrade(Grade.valueOf(query.toUpperCase()));

            case "role":
                return memberRepository.findByRole(Role.valueOf(query.toUpperCase()));

            case "mLevel":
                return memberRepository.findBymLevel(Integer.parseInt(query));

            default:
                throw new IllegalArgumentException("Invalid category: " + category);
        }
    }
    
}
