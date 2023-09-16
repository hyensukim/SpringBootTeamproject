package com.springboot.shootformoney.admin.controller.membercontroller;

import com.springboot.shootformoney.admin.service.memberservice.MemberManagementService;
import com.springboot.shootformoney.member.entity.Member;
import com.springboot.shootformoney.member.enum_.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@Controller
@RequestMapping("/admin/member") // API TEST를 위해 주석 처리
@RequiredArgsConstructor
public class MemberManagementController {

    private final MemberManagementService memberInfoService;

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
        memberInfoService.deleteMember(mId);
        return ResponseEntity.noContent().build();
    }

    // 회원 권한 변경
    @PutMapping("/{mId}/role")
    public ResponseEntity<?> changeMemberRole(@PathVariable String mId, @RequestBody Role newRole){
        try {
            memberInfoService.changeMemberRole(mId, newRole);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            // 예외가 발생시 해당 예외 메시지와 함께 HTTP 상태 코드 400(Bad Request)를 반환
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
