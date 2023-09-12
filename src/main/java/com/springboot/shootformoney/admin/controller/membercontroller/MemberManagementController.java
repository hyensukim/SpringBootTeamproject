package com.springboot.shootformoney.admin.controller.membercontroller;

import com.springboot.shootformoney.admin.service.memberservice.MemberAuthorityService;
import com.springboot.shootformoney.admin.service.memberservice.MemberInfoService;
import com.springboot.shootformoney.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
//@RequestMapping("/admin/member")
@RequiredArgsConstructor
public class MemberManagementController {

    private final MemberAuthorityService memberAuthorityService;
    private final MemberInfoService memberInfoService;

    // 전체 회원 조회
    @GetMapping("/")
    public ResponseEntity<List<Member>> getAllMember(){
        List<Member> members = memberInfoService.findAllMembers();
        return ResponseEntity.ok(members);
    }

    // 단일 회원 조회 (mNo)
    @GetMapping("/{mId}")
    public ResponseEntity<Member> getOneMembers(@PathVariable String mId){
        Member member = memberInfoService.findMember(mId);
        return ResponseEntity.ok(member);
    }

    // 회원 제재 (탈퇴)
    @DeleteMapping("/{mId}")
    public ResponseEntity<Void> deleteMember(@PathVariable String mId){
        memberAuthorityService.deleteMember(mId);
        return ResponseEntity.noContent().build();
    }
}
