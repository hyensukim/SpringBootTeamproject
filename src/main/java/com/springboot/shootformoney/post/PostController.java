package com.springboot.shootformoney.post;

import com.springboot.shootformoney.board.entity.Board;
import com.springboot.shootformoney.board.repository.BoardRepository;
import com.springboot.shootformoney.member.entity.Member;
import com.springboot.shootformoney.member.repository.MemberRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final MemberRepository memberRepository;
    private  final BoardRepository boardRepository;

    // 페이징 처리한 전체 목록 조회
    @GetMapping("/all") 
    public String getAllPosts(@ModelAttribute PostSearchInfo postSearchInfo,Model model) {
        Page<Post> pageList = postService.getAllWithPage(postSearchInfo);
        List<Post> postList = pageList.getContent();

        int nowPage = pageList.getPageable().getPageNumber() + 1; // 현재 페이지
        int startPage = (nowPage-1) / 10 * 10 + 1; // 첫페이지
        int endPage = Math.min(startPage + 10 - 1, pageList.getTotalPages());

        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);
        model.addAttribute("nowPage",nowPage);
        model.addAttribute("postList", postList);
        return "post/posts";
    }

    // 게시글 작성
    @GetMapping("/create")
    public String createPostForm(@ModelAttribute("postDto") PostDTO postDto, Model model) {
        List<Board> boards = boardRepository.findAll();
        model.addAttribute("boards",boards);
        return "post/addForm";
    }
    
    @PostMapping("/create")
    public String createPost(@ModelAttribute("postDto") @Valid PostDTO postDto,
                             Model model) {
        try {
            postService.savePost(postDto);
            model.addAttribute("postDto",postDto); // 모델에 새 게시글 추가

        }catch(Exception e){
            String script = String.format("Swal.fire({title: '%s', text: '%s', icon: 'error'})" +
                    ".then(function() { location.href='/post/create'; })", "Error!", e.getMessage());
            model.addAttribute("script", script);
            return "script/sweet";
        }

        return "redirect:/post/all"; // 'post/all' 뷰로 이동
    }

    // 게시판별 목록 조회 - 추가 구현(Service - if)
    @GetMapping("/all/{bNo}")
    public String getAllPostsByBoard(@PathVariable Long bNo, @ModelAttribute PostSearchInfo postSearchInfo, Model model) {
        postSearchInfo.setBNo(bNo);
        Page<Post> pageList = postService.getAllWithPage(postSearchInfo);
        List<Post> postList = pageList.getContent();

        int nowPage = pageList.getPageable().getPageNumber() + 1; // 현재 페이지
        int startPage = (nowPage-1) / 10 * 10 + 1; // 첫페이지
        int endPage = Math.min(startPage + 10 - 1, pageList.getTotalPages());

        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("postList", postList);

        return "post/posts2";
    }

    // 단일 게시글 조회
    @GetMapping("/detail/{pNo}")
    public String getOnePost(@PathVariable Long pNo, Model model) {
        try {
            Post post = postService.findPost(pNo);
            model.addAttribute("post", post);
        }catch(Exception e){
            String script = String.format("Swal.fire({title: '%s', text: '%s', icon: 'error'})" +
                    ".then(function() { location.href='/post/create'; })", "Error!", e.getMessage());
            model.addAttribute("script", script);
            return "script/sweet";
        }
        return "post/detail";
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
        return "redirect:/post/all";
    }



    // 전체 게시글 조회
//    @GetMapping("/")
//    public ResponseEntity<List<Post>> getAllPosts() {
//        List<Post> posts = postService.findAllPosts();
//        return ResponseEntity.ok(posts);
//    }


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
        Post post = postService.findPost(pNo);
        model.addAttribute("post", post);
        return "post/view";
    }

    // 게시글 수정
//    @GetMapping("/{pNo}")
//    public String editPostForm(@PathVariable Long pNo, Model model) {
//        Post post = postService.findPost(pNo);
//        model.addAttribute("post", post);
//        return "edit";
//    }

    @GetMapping("/{pNo}/edit")
    public String editPostForm(@PathVariable Long pNo, Model model) {
        PostDTO post = PostDTO.of(postService.findPost(pNo));  // getPost() 메서드는 해당 번호의 게시글 정보를 반환해야 합니다.
        model.addAttribute("post", post);
        return "post/edit";
    }


    @PutMapping("/{pNo}/edit")
    public String editPostPs(@PathVariable Long pNo, @ModelAttribute("post") @Valid PostDTO updatedPost, Model model) {

        postService.updatePost(pNo, updatedPost.getPTitle(), updatedPost.getPContent());

        return "post/edit";
    }

}