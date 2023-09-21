package com.springboot.shootformoney.admin.controller.postcontroller;

import com.springboot.shootformoney.PageHandler;
import com.springboot.shootformoney.admin.service.postservice.PostAdminService;
import com.springboot.shootformoney.admin.service.postservice.PostFindService;
import com.springboot.shootformoney.board.entity.Board;
import com.springboot.shootformoney.board.repository.BoardRepository;
import com.springboot.shootformoney.post.Post;
import com.springboot.shootformoney.post.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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


//     모든 게시물 조회
    @GetMapping("/postList/all")
    public String getAllPosts(Model model) {
        List<Post> posts = postService.findAllPosts();

//        int startPage = 1;  // 시작 페이지
//        int endPage = 10;  // 끝 페이지
//        model.addAttribute("startPage", startPage);
//        model.addAttribute("endPage", endPage);

        model.addAttribute("posts", posts);
        model.addAttribute("pageTitle", "게시물 관리");
        return "admin/postManagement";
    }

//    @GetMapping("/postList/all")
//    public String getAllPosts(@RequestParam(name = "page", defaultValue = "1") int page,
//                              @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
//                              Model model) {
//        // 데이터베이스에서 게시글 목록을 가져옵니다.
//        Page<Post> postPage = postFindService.findPosts(PageRequest.of(page - 1, pageSize));
//
//        // 총 게시글 수와 페이지당 게시글 수를 모델에 추가합니다.
//        long totalCnt = postPage.getTotalElements();
//        model.addAttribute("posts", postPage.getContent());
//        model.addAttribute("totalCnt", totalCnt);
//        model.addAttribute("pageSize", pageSize);
//        model.addAttribute("currentPage", page);
//        model.addAttribute("pageTitle", "게시글 관리");
//        return "admin/postManagement";
//    }


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

//    // 모든 게시물 조회
//    @GetMapping("/postList/all")
//    public String getAllPosts(Model model,
//                              @RequestParam(value = "page", defaultValue = "1") int page) {
//        List<Post> posts = postService.findAllPosts();
//        model.addAttribute("posts", posts);
//
//        // 페이지 핸들러 객체 생성 및 추가
//        int totalCnt = posts.size();  // 총 게시글 수
//        PageHandler pageHandler = new PageHandler(totalCnt, page);
//        model.addAttribute("pageHandler", pageHandler);
//
//        model.addAttribute("pageTitle", "게시물 관리");
//
//        return "admin/postManagement";
//    }
//
//    // 컨트롤러
//    @GetMapping("/postList")
//    public String postList(@RequestParam(required = false) String bName,
//                           @RequestParam(value = "page", defaultValue = "1") int page,
//                           Model model){
//
//        List<Board> boards = boardRepository.findAll();
//        model.addAttribute("boards", boards);
//
//        List<Post> posts;
//        if (bName != null) {
//            posts = postFindService.searchPosts("bName", bName);
//        } else {
//            posts = postFindService.findAllposts();
//        }
//
//        model.addAttribute("posts", posts);
//        // 페이지 핸들러 객체 생성 및 추가
//        int totalCnt = posts.size();  // 총 게시글 수 (특정 보드의 경우 해당 보드의 게시글 수)
//        PageHandler pageHandler = new PageHandler(totalCnt, page);
//        model.addAttribute("pageHandler", pageHandler);
//
//        return "/admin/postList";
//    }

}
