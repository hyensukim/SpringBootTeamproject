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

    private final MemberManagementService memberInfoService;

    //    // 전체 회원 조회
//    @GetMapping("/memberList/all")
//    public String getAllMember(Model model) {
//        List<Member> members = memberInfoService.findAllMembers();
//        model.addAttribute("members", members);
//
//        model.addAttribute("pageTitle", "회원 관리");
//
//        return "admin/memberManagement";
//    }

    // 회원 전체 조회 (+ 페이징 처리)
    @GetMapping("/memberList/all")
    public String getAllMember(@ModelAttribute AdminSearchInfo pageInfo, Model model) {

        model.addAttribute("pageTitle", "회원관리");

        try {
            Page<Member> members = memberInfoService.getsAdminMemberWithPages(pageInfo);
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

    // 카테고리별 멤버 조회
    @GetMapping("/memberList/search")
    public String searchMember(@RequestParam String category, @RequestParam String query, Model model) {

        List<Member> members = memberInfoService.searchMembers(category, query);
        model.addAttribute("members", members);

        switch (category) {
            case "mNo":
                model.addAttribute("pageTitle", "회원 조회 | 회원번호");
                break;

            case "mId":
                model.addAttribute("pageTitle", "회원 조회 | ID");
                break;

            case "mName":
                model.addAttribute("pageTitle", "회원 조회 | 이름");
                break;

            case "mNickName":
                model.addAttribute("pageTitle", "회원 조회 | 별명");
                break;

            case "grade":
                model.addAttribute("pageTitle", "회원 조회 | 등급");
                break;

            case "mLevel":
                model.addAttribute("pageTitle", "회원 조회 | Level");
                break;

            case "role":
                model.addAttribute("pageTitle", "회원 조회 | 권한");
                break;


            default:
                throw new IllegalArgumentException("Invalid category: " + category);
        }

        return "admin/memberList";
    }

    // 회원 제재 (탈퇴)
    @DeleteMapping("/{mId}")
    public RedirectView deleteMember(@PathVariable String mId) {
        memberInfoService.deleteMember(mId);
        return new RedirectView("/admin/member/memberList/all");
    }

    // 회원 권한 변경
    @PutMapping("/{mId}/role")
    public RedirectView changeMemberRole(@PathVariable String mId, @RequestParam("newRole") Role newRole) {
        try {
            memberInfoService.changeMemberRole(mId, newRole);
            return new RedirectView("/admin/member/memberList/all");
        } catch (IllegalArgumentException e) {
            // 예외가 발생시 해당 예외 메시지와 함께 HTTP 상태 코드 400(Bad Request)를 반환
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

}