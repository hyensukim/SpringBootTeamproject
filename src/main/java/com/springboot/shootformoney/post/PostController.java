package com.springboot.shootformoney.post;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;


    //게시글 생성
    @PostMapping("/")
    public ResponseEntity<Long> createPost(@RequestBody PostDTO postDto) {
        Post post = new Post(postDto.getPTitle(), postDto.getPContent());
        Long id = postService.savePost(post);
        return ResponseEntity.ok(id);
    }

    //삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }

    // 게시글 수정
    @PutMapping("/{id}")
    public ResponseEntity<Void> updatePost(@PathVariable Long id, @RequestBody PostDTO updatedPost) {
        postService.updatePost(id, updatedPost.getPTitle(), updatedPost.getPContent());
        return ResponseEntity.noContent().build();
    }

    // 전체 게시글 조회
    @GetMapping("/")
    public ResponseEntity<List<Post>> getAllPosts() {
        List<Post> posts = postService.findAllPosts();
        return ResponseEntity.ok(posts);
    }

    // 단일 게시글 조회
    @GetMapping("/{id}")
    public ResponseEntity<Post> getOnePosts(@PathVariable Long id) {
        Post post = postService.findPost(id);
        return ResponseEntity.ok(post);
    }

    //제목으로 찾기
    @GetMapping("/title/{title}")
    public ResponseEntity<List<Post>> getPostsByTitle(@PathVariable String title) {
        List<Post> posts = postService.findPostsByTitle(title);
        return ResponseEntity.ok(posts);
    }

    //아이디로 찾기
   //   @GetMapping("/member/{mId}")
  //  public ResponseEntity<List<Post>> getPostsByMember(@PathVariable String mId) {
  //      List<Post> posts = postService.findPostsByMemberId(mId);
  //      return ResponseEntity.ok(posts);
  //  }


}