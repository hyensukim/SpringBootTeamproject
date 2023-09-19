package com.springboot.shootformoney.admin.controller.postcontroller;

import com.springboot.shootformoney.admin.service.postservice.PostAdminService;
import com.springboot.shootformoney.post.Post;
import com.springboot.shootformoney.post.PostDTO;
import com.springboot.shootformoney.post.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//@RestController
@Controller
@RequestMapping("/admin/post")
public class PostManagementController {

    @Autowired
    private PostAdminService postAdminService;

    @Autowired
    private PostService postService;

//    // 모든 게시물 조회
//    @GetMapping("/")
//    public ResponseEntity<List<PostDTO>> getAllPosts() {
//        return new ResponseEntity<>(postAdminService.findAllPosts(), HttpStatus.OK);
//    }
//
//    // 게시판 제목별 모든 게시물 조회
//    @GetMapping("/boardSearch/{bName}")
//    public ResponseEntity<List<PostDTO>> getPostsByBoard(@PathVariable String bName) {
//        return new ResponseEntity<>(postAdminService.findPostsByBoardName(bName), HttpStatus.OK);
//    }

    // 모든 게시물 조회
    @GetMapping("/postList/all")
    public String getAllPosts(Model model) {
        List<Post> posts = postService.findAllPosts();
        model.addAttribute("posts", posts);
        model.addAttribute("pageTitle", "게시물 관리");
        return "admin/postManagement"; 
    }

//    // 게시판 제목별 모든 게시물 조회
//    @GetMapping("/boardSearch/{bName}")
//    public String getPostsByBoard(@PathVariable String bName, Model model) {
//        model.addAttribute("posts", postAdminService.findPostsByBoardName(bName));
//        model.addAttribute("pageTitle", "게시물 관리");
//        return "admin/postManagement";
//    }

    // 게시판 제목별 모든 게시물 조회
    @GetMapping("/boardSearch/{bName}")
    public String getPostsByBoard(@PathVariable String bName, Model model) {
        List<PostDTO> posts = postAdminService.findPostsByBoardName(bName);
        model.addAttribute("posts", posts);
        model.addAttribute("pageTitle", "게시물 관리");
        return "admin/postManagement";
    }

    // 단일 게시글 조회
    @GetMapping("/postSearch/{pNo}")
    public ResponseEntity<Post> getOnePosts(@PathVariable Long pNo) {
        Post post = postService.findPost(pNo);
        return ResponseEntity.ok(post);
    }

    // 게시글 제목으로 찾기
    @GetMapping("/titleSearch/{pTitle}")
    public ResponseEntity<List<Post>> getPostsByTitle(@PathVariable String pTitle) {
        List<Post> posts = postService.findPostsByTitle(pTitle);
        return ResponseEntity.ok(posts);
    }

    // 회원 닉네임별 모든 게시물 조회
    @GetMapping("/nicknameSearch/{mNickname}")
    public ResponseEntity<List<PostDTO>> getPostsByMemberNickname(@PathVariable String mNickname) {
        return new ResponseEntity<>(postAdminService.findPostsByMemberNickname(mNickname), HttpStatus.OK);
    }

    // 게시글 삭제
    @DeleteMapping("/{pNo}")
    public String deletePost(@PathVariable Long pNo) {
        postAdminService.deletePost(pNo);
        return "redirect:/admin/post/postList/all";
    }

//    // 게시글 삭제
//    @DeleteMapping("/{pNo}")
//    public ResponseEntity<Void> deletePost(@PathVariable Long pNo) {
//        postAdminService.deletePost(pNo);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }

}
