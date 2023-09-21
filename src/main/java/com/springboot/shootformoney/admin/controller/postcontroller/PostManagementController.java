package com.springboot.shootformoney.admin.controller.postcontroller;

import com.springboot.shootformoney.admin.dto.postdto.PostSearchInfo;
import com.springboot.shootformoney.admin.service.postservice.PostAdminService;
import com.springboot.shootformoney.admin.service.postservice.PostFindService;
import com.springboot.shootformoney.board.entity.Board;
import com.springboot.shootformoney.board.repository.BoardRepository;
import com.springboot.shootformoney.post.Post;
import com.springboot.shootformoney.post.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@RestController
@Controller
@RequestMapping("/admin/post")
public class PostManagementController {

    @Autowired
    private PostAdminService postAdminService;
    @Autowired
    private PostFindService postFindService;
    @Autowired
    private PostService postService;
    @Autowired
    private BoardRepository boardRepository;

    // 모든 게시물 조회 (+페이징 처리)
    @GetMapping("/postList/all")
//    public String getAllPosts(@ModelAttribute PostSearchInfo pageInfo, Model model) {
    public String getAllPosts(@ModelAttribute PostSearchInfo pageInfo
//    public String getAllPosts(@PathVariable Long bNo, @ModelAttribute PostSearchInfo pageInfo
            , Model model) {

        model.addAttribute("pageTitle", "게시물 관리");

        try {
//            Page<Post> posts = postFindService.getsAdminPostWithPages(pageInfo, bNo);
            Page<Post> posts = postFindService.getsAdminPostWithPages(pageInfo);
            List<Post> postList = posts.getContent();

            int nowPage = posts.getPageable().getPageNumber() + 1; //현재 페이지
            int startPage = (nowPage - 1) / 10 * 10 + 1; // 첫 페이지
            int endPage = Math.min(startPage + 10 -1, posts.getTotalPages()); // 마지막 페이지

            model.addAttribute("postList", postList);
            model.addAttribute("nowPage", nowPage);
            model.addAttribute("startPage", startPage);
            model.addAttribute("endPage", endPage);
        } catch (NullPointerException e) {
            String script = String.format("Swal.fire('%s','','error')" +
                    ".then(function(){history.back();})",e.getMessage());
        }

        return "admin/postManageMent";
    }

    // 게시글 상세보기
    // 추후에 실제 post 뷰로 넘어가는 것으로 구현 예정
    @GetMapping("/{pNo}")
    public String getPost(@PathVariable Long pNo, Model model) {
        Post post = postFindService.findPostPNo(pNo);
        model.addAttribute("post", post);
        model.addAttribute("pageTitle", "게시글");
        return "admin/postDetail";  // 게시글 상세 정보를 보여주는 뷰 이름
    }


    @GetMapping("/postList")
    public String postList(@RequestParam(required = false) String bName, Model model) {
        List<Board> boards = boardRepository.findAll();
        model.addAttribute("boards", boards);

        List<Post> posts;
        if (bName != null) {
            posts = postFindService.searchPosts("bName", bName);
        } else {
            posts = postFindService.findAllposts();
        }

        model.addAttribute("posts", posts);

        return "/admin/postList";
    }


    // 각 기능 별 게시글 조회
    @GetMapping("/postList/search")
    public String searchPost(@RequestParam String category, @RequestParam String query, Model model) {

        List<Post> posts = postFindService.searchPosts(category, query);
        model.addAttribute("posts", posts);

        switch (category) {
            case "pNo":
                model.addAttribute("pageTitle", "게시글 조회 | 번호");
                break;

            case "mId":
                model.addAttribute("pageTitle", "게시글 조회 | 작성자 ID");
                break;

            case "mNickName":
                model.addAttribute("pageTitle", "게시글 조회 | 작성자 별명");
                break;

            case "bName":
                model.addAttribute("pageTitle", "게시글 조회 | 게시판 이름");
                break;

            case "pTitle":
                model.addAttribute("pageTitle", "게시글 조회 | 게시글 제목");
                break;

            default:
                throw new IllegalArgumentException("Invalid category: " + category);
        }

        return "admin/postList";
    }

//    @GetMapping("/postList/search")
//    public String searchPost(@ModelAttribute PostSearchInfo postSearchInfo, Model model) {
//        Page<Post> adminPostList = postFindService.getsAdminPostWithPages(postSearchInfo);
//        model.addAttribute("postList", adminPostList.getContent());
//        model.addAttribute("nowPage", adminPostList.getNumber() + 1);
//        model.addAttribute("startPage", adminPostList.getNumber() / 10 * 10 + 1);
//        model.addAttribute("endPage", Math.min(adminPostList.getNumber() / 10 * 10 + 10, adminPostList.getTotalPages()));
//        model.addAttribute("pageTitle", "게시물 관리");
//        return "admin/postManageMent";
//    }

    // 게시글 삭제
    @DeleteMapping("/{pNo}")
    public String deletePost(@PathVariable Long pNo) {
        postAdminService.deletePost(pNo);
        return "redirect:/admin/post/postList/all";
    }

}
