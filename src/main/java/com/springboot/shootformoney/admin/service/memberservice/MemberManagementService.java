package com.springboot.shootformoney.admin.service.memberservice;

import com.springboot.shootformoney.admin.dto.AdminSearchInfo;
import com.springboot.shootformoney.board.entity.Board;
import com.springboot.shootformoney.member.entity.Member;
import com.springboot.shootformoney.member.enum_.Grade;
import com.springboot.shootformoney.member.enum_.Role;
import com.springboot.shootformoney.member.repository.MemberRepository;
import com.springboot.shootformoney.post.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.springframework.data.domain.Sort.by;

/*
 관리자 - 회원 조회 서비스
 */

@Service
@RequiredArgsConstructor
public class MemberManagementService {

    private final MemberRepository memberRepository;

    // 회원 전체 조회
    public List<Member> findAllMembers() {
        return memberRepository.findAll();
    }

    // 회원 단일 조회 (id)
    public Member findMember(String mId) {
        Member member = memberRepository.findBymId(mId);
        if (member != null) {
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
    public void deleteMember(String mId) {
        Member member = memberRepository.findBymId(mId);
        if (member != null) {
            memberRepository.delete(member);
        } else {
            throw new IllegalArgumentException("존재하지 않는 회원입니다.");
        }
    }

    // 회원 전체 조회(페이징 처리)
    public Page<Member> getsAdminMemberWithPages(AdminSearchInfo adminSearchInfo) {

        int page = adminSearchInfo.getPage();
        int pageSize = adminSearchInfo.getPageSize();
        page = Math.max(page, 1);
        pageSize = pageSize < 1 ? 15 : pageSize;

        // 권한 오름차순, 생성일자 내림차순으로 정렬
        Pageable pageable = PageRequest.of(page - 1, pageSize,
                Sort.by(Sort.Order.asc("role"), Sort.Order.desc("createdAt")));

        Page<Member> adminMemberList = memberRepository.findAll(pageable);

        return adminMemberList;

    }

    public Page<Member> searchMembers(AdminSearchInfo adminSearchInfo) {
        int page = adminSearchInfo.getPage();
        int pageSize = adminSearchInfo.getPageSize();

        page = Math.max(page, 1);
        pageSize = pageSize < 1 ? 15 : pageSize;

        String sOpt = adminSearchInfo.getSOpt();
        String sKey = adminSearchInfo.getSKey();

        Pageable pageable = PageRequest.of(page - 1, pageSize,
                Sort.by(Sort.Order.asc("role"), Sort.Order.desc("createdAt")));

        if (sOpt != null && sKey != null) {
            switch (sOpt) {
                case "mNo":
                    Long mNo = Long.parseLong(sKey);
                    return memberRepository.findBymNo(mNo,pageable);
                case "mId":
                    return memberRepository.findBymId(sKey, pageable);
                case "mName":
                    return memberRepository.findBymName(sKey, pageable);
                case "mNickName":
                    return memberRepository.findBymNickName(sKey, pageable);
                case "grade":
                    Grade grade = Grade.valueOf(sKey);
                    return memberRepository.findByGrade(grade, pageable);
                case "mLevel":
                    Integer mLevel = Integer.parseInt(sKey);
                    return memberRepository.findBymLevel(mLevel, pageable);
                case "role":
                    Role role = Role.valueOf(sKey);
                    return memberRepository.findByRole(role, pageable);
            }
        }

        return memberRepository.findAll(pageable);
    }
}
