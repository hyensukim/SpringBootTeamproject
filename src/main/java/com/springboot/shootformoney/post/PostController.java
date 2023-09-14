package com.springboot.shootformoney.post;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;


    //게시글 생성
    @PostMapping("/")
    public ResponseEntity<Long> createPost(@Valid @RequestBody PostDTO postDto) {
        Long id = postService.savePost(postDto);
        return ResponseEntity.created(URI.create("/posts/" + id)).body(id);
    }

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

    // 단일 게시글 조회
    @GetMapping("/{pNo}")
    public ResponseEntity<Post> getOnePosts(@PathVariable Long pNo) {
        Post post = postService.findPost(pNo);
        return ResponseEntity.ok(post);
    }

    //제목으로 찾기
    @GetMapping("/tTitle/{pTitle}")
    public ResponseEntity<List<Post>> getPostsByTitle(@PathVariable String pTitle) {
        List<Post> posts = postService.findPostsByTitle(pTitle);
        return ResponseEntity.ok(posts);
    }

    //아이디로 찾기
   //   @GetMapping("/member/{mId}")
  //  public ResponseEntity<List<Post>> getPostsByMember(@PathVariable String mId) {
  //      List<Post> posts = postService.findPostsByMemberId(mId);
  //      return ResponseEntity.ok(posts);
  //  }


}