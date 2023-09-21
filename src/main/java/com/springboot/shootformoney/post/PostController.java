package com.springboot.shootformoney.post;

import com.springboot.shootformoney.board.entity.Board;
import com.springboot.shootformoney.board.repository.BoardRepository;
import com.springboot.shootformoney.member.entity.Member;
import com.springboot.shootformoney.member.repository.MemberRepository;
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
    private final MemberRepository memberRepository;
    private  final BoardRepository boardRepository;

//    @GetMapping("/posts/all")
//    public String getAllPosts(@RequestParam(name = "bNo", required = false) Long bNo, Model model) {
//        List<Post> posts;
//
//        if (bNo != null) {
//            posts = postService.findPostsByBoardBNo(bNo);
//        } else {
//            posts = postService.findAllPosts();
//        }
//        model.addAttribute("posts", posts);
//        return "posts";
//
//    }
    @GetMapping("/all") //추가
    public String getAllPosts(Model model) {
        List<Post> posts = postService.findAllPosts();
        model.addAttribute("posts", posts);
        return "posts";
    }
    @GetMapping("/{param}") //추가
    public String handleStringParam(@PathVariable String param, Model model) {
        if ("all".equals(param)) {
            List<Post> posts = postService.findAllPosts();
            model.addAttribute("posts", posts);
            return "posts";
        } else {
            // 잘못된 경로 파라미터에 대한 처리 코드...
            throw new IllegalArgumentException("Invalid Path Variable");
        }
    }

    @GetMapping("/create")
    public String createPostForm(Model model) {
        List<Member> members = memberRepository.findAll();
        List<Board> boards = boardRepository.findAll();

        // 모델 객체 생성 및 필드 설정
        PostDTO postDto = new PostDTO();
        postDto.setBNo(1L); // 예시로 필드 값을 설정
        postDto.setPTitle("제목"); // 예시로 필드 값을 설정

        model.addAttribute("postDto", postDto);
        model.addAttribute("members", members);
        model.addAttribute("boards", boards);

        return "addForm";
    }

    //게시글 생성
    @PostMapping("/create")
    public String createPost(@ModelAttribute("postDto") @Valid PostDTO postDto, Model model) {
        Long pNo = postService.savePost(postDto);
        PostDTO newPost = postService.findPost(pNo); // 새로 생성된 게시글 조회

        model.addAttribute("post", newPost); // 모델에 새 게시글 추가
        return "redirect:/posts/all"; // 'posts/all' 뷰로 이동
    }

    // 단일 게시글 조회
    @GetMapping("/{pNo}")
    public String getOnePost(@PathVariable Long pNo, Model model) {
        PostDTO post = postService.findPost(pNo);
        model.addAttribute("post", post);
        return "detail";

    }
    // 매핑

    //삭제
//    @PostMapping("/{pNo}/delete")
//    public String deletePost(@PathVariable Long pNo) {
//        postService.deletePost(pNo);
//        return "redirect:/posts/all";
//
//    }

    //삭제(수정)
    @DeleteMapping("/{pNo}")
    public String deletePost(@PathVariable Long pNo) {
        postService.deletePost(pNo);
        return "redirect:/posts/all";
    }

    // 게시글 수정
    @PostMapping("/{pNo}/edit")
    public String editPost(@PathVariable Long pNo, @ModelAttribute("post") @Valid PostDTO updatedPost) {
        postService.updatePost(pNo, updatedPost.getPTitle(), updatedPost.getPContent());
        return "redirect:/posts/{pNo}";
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

    @PostMapping("/{pNo}/detail")
    public String getPostDetail(@PathVariable Long pNo, Model model) {
        PostDTO post = postService.findPost(pNo);
        model.addAttribute("post", post);
        return "post/view";
    }

    @GetMapping("/{pNo}/edit")
    public String editPostForm(@PathVariable Long pNo, Model model) {
        PostDTO post = postService.findPost(pNo);
        model.addAttribute("post", post);
        return "post/edit";
    }
}