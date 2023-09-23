package com.springboot.shootformoney.admin.controller.membercontroller;

import com.springboot.shootformoney.admin.dto.AdminSearchInfo;
import com.springboot.shootformoney.admin.service.memberservice.MemberManagementService;
import com.springboot.shootformoney.member.entity.Member;
import com.springboot.shootformoney.member.enum_.Role;
import com.springboot.shootformoney.post.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Arrays;
import java.util.List;

//@RestController
@Controller
@RequestMapping("/admin/member")
@RequiredArgsConstructor
public class MemberManagementController {

    private final MemberManagementService memberManagementService;

    // 회원 전체 조회 (+ 페이징 처리)
    @GetMapping("/memberList/all")
    public String getAllMember(@ModelAttribute AdminSearchInfo pageInfo, Model model) {

        model.addAttribute("pageTitle", "회원관리");

        try {
            Page<Member> members = memberManagementService.getsAdminMemberWithPages(pageInfo);
            List<Member> memberList = members.getContent();

            int nowPage = members.getPageable().getPageNumber() + 1; //현재 페이지
            int startPage = (nowPage - 1) / 10 * 10 + 1; // 첫 페이지
            int endPage = Math.min(startPage + 10 - 1, members.getTotalPages()); // 마지막 페이지

            model.addAttribute("memberList", memberList);
            model.addAttribute("nowPage", nowPage);
            model.addAttribute("startPage", startPage);
            model.addAttribute("endPage", endPage);
        } catch (NullPointerException e) {
            String script = String.format("Swal.fire('%s','','error')" +
                    ".then(function(){history.back();})", e.getMessage());
        }

        return "admin/memberManagement";
    }

    // 회원 검색 조회 (+ 페이징 처리)
    @GetMapping("/memberList/search")
    public String getAllMembers(@ModelAttribute AdminSearchInfo searchInfo,
                                Model model) {

        model.addAttribute("pageTitle", "회원관리");

        try {
            // 회원 조회
            Page<Member> members = memberManagementService.searchMembers(searchInfo);

            List<Member> membersList= members.getContent();

            int nowPage= members.getPageable().getPageNumber() + 1; // 현재 페이지
            int startPage= (nowPage - 1) / 10 * 10 + 1; // 첫 페이지
            int endPage= Math.min(startPage + 10 - 1 ,members.getTotalPages()); // 마지막 페이지

            model.addAttribute("memberList", membersList);
            model.addAttribute("nowPage", nowPage);
            model.addAttribute("startPage", startPage);
            model.addAttribute("endPage", endPage);

        } catch (NullPointerException e) {
            String script = String.format(
                    "Swal.fire('%s','','error').then(function(){history.back();})", e.getMessage());
        }

        return "admin/memberList";
    }

    // 회원 제재 (탈퇴)
    @DeleteMapping("/{mId}")
    public RedirectView deleteMember(@PathVariable String mId) {
        memberManagementService.deleteMember(mId);
        return new RedirectView("/admin/member/memberList/all");
    }

    // 회원 권한 변경
    @PutMapping("/{mId}/role")
    public RedirectView changeMemberRole(@PathVariable String mId, @RequestParam("newRole") Role newRole) {
        try {
            memberManagementService.changeMemberRole(mId, newRole);
            return new RedirectView("/admin/member/memberList/all");
        } catch (IllegalArgumentException e) {
            // 예외가 발생시 해당 예외 메시지와 함께 HTTP 상태 코드 400(Bad Request)를 반환
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

}