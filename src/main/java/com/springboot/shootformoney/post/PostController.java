package com.springboot.shootformoney.post;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Controller
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/")
    public String getAllPosts(Model model) {
        List<Post> posts = postService.findAllPosts();
        model.addAttribute("posts", posts);
        return "post/list";  // 매핑
    }

    @GetMapping("/create")
    public String createPostForm(Model model) {
        model.addAttribute("postDto", new PostDTO());
        return "post/create";
    }

    //게시글 생성
    @PostMapping("/")
    public String createPost(@ModelAttribute("postDto") @Valid PostDTO postDto) {
        Long id = postService.savePost(postDto);
        return "redirect:/posts/" + id;
    }

    // 단일 게시글 조회
    @GetMapping("/{pNo}")
    public String getOnePosts(@PathVariable Long pNo, Model model) {
        PostDTO post = postService.findPost(pNo);
        model.addAttribute("post", post);
        return "post/view";
    }
    // 매핑

    //삭제
    @DeleteMapping("/{pNo}")
    public ResponseEntity<Void> deletePost(@PathVariable Long pNo) {
        postService.deletePost(pNo);
        return ResponseEntity.noContent().build();
    }

    // 게시글 수정
    @PutMapping("/{pNo}")
    public ResponseEntity<Void> updatePost(@PathVariable Long pNo, @Valid @RequestBody 	PostDTO updatedPost) {
        postService.updatePost(pNo, updatedPost.getPTitle(), updatedPost.getPContent());
        return 	ResponseEntity.noContent().build();
    }

    // 전체 게시글 조회
    @GetMapping("/")
    public ResponseEntity<List<Post>> getAllPosts() {
        List<Post> posts = postService.findAllPosts();
        return ResponseEntity.ok(posts);
    }


    //제목으로 찾기
    @GetMapping("/tTitle/{pTitle}")
    public ResponseEntity<List<Post>> getPostsByTitle(@PathVariable String pTitle) {
        List<Post> posts = postService.findPostsByTitle(pTitle);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/member/nickname/{mNickName}")
    public ResponseEntity<List<Post>> getPostsByMemberNickname(@PathVariable String mNickName) {
        List<Post> posts = postService.findPostsByMemberNickName(mNickName);
        return ResponseEntity.ok(posts);
    }


}