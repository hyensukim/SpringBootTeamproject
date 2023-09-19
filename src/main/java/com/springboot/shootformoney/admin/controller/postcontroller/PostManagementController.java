package com.springboot.shootformoney.admin.controller.postcontroller;

import com.springboot.shootformoney.admin.service.postservice.PostAdminService;
import com.springboot.shootformoney.admin.service.postservice.PostFindService;
import com.springboot.shootformoney.board.entity.Board;
import com.springboot.shootformoney.board.repository.BoardRepository;
import com.springboot.shootformoney.post.Post;
import com.springboot.shootformoney.post.PostService;
import org.springframework.beans.factory.annotation.Autowired;
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

    // 모든 게시물 조회
    @GetMapping("/postList/all")
    public String getAllPosts(Model model) {
        List<Post> posts = postService.findAllPosts();
        model.addAttribute("posts", posts);
        model.addAttribute("pageTitle", "게시물 관리");
        return "admin/postManagement";
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

    // 컨트롤러
    @GetMapping("/postList")
    public String postList(@RequestParam(required = false) String bName, Model model){
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
    public String searchPost(@RequestParam String category, @RequestParam String query, Model model){

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

    // 게시글 삭제
    @DeleteMapping("/{pNo}")
    public String deletePost(@PathVariable Long pNo) {
        postAdminService.deletePost(pNo);
        return "redirect:/admin/post/postList/all";
    }

}
