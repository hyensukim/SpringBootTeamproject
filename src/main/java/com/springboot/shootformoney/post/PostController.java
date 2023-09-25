package com.springboot.shootformoney.post;

import com.springboot.shootformoney.board.entity.Board;
import com.springboot.shootformoney.board.repository.BoardRepository;
import com.springboot.shootformoney.comment.dto.CommentRequestDto;
import com.springboot.shootformoney.comment.entity.Comment;
import com.springboot.shootformoney.comment.service.CommentService;
import com.springboot.shootformoney.member.dto.SearchInfo;
import com.springboot.shootformoney.member.entity.Member;
import com.springboot.shootformoney.member.repository.MemberRepository;
import com.springboot.shootformoney.member.utils.MemberUtil;
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

    private final MemberUtil memberUtil;
    private final PostService postService;
    private  final BoardRepository boardRepository;
    private  final CommentService commentService;

    // 페이징 + 검색 전체 목록 조회
    @GetMapping("/all")
    public String getAllPostsByTitle(@ModelAttribute PostSearchInfo postSearchInfo, Model model) {
        //검색 옵션과 검색어를 이용하여 게시글 검색
        Page<Post> pageList = postService.gets(postSearchInfo);
        List<Post> postList = pageList.getContent();


        int nowPage = pageList.getPageable().getPageNumber() + 1; // 현재 페이지
        int startPage = (nowPage-1) / 10 * 10 + 1; // 첫페이지
        int endPage = Math.min(startPage + 10 - 1, pageList.getTotalPages()); // 마지막 페이지

        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        //검색 결과를 모델에 추가
        model.addAttribute("postList", postList);

        return "post/posts";
    }

    // 게시글 작성
    @GetMapping("/create")
    public String createPostForm(@ModelAttribute("postDto") PostDTO postDto, Model model) {
        if(memberUtil.isLogin()) {
            List<Board> boards = boardRepository.findAll();
            model.addAttribute("boards", boards);
            return "post/addForm";
        }else{
            String script = String.format("Swal.fire({title: '접근 불가!', text: '로그인 후 클릭해주세요.', icon: 'error'})" +
                    ".then(function() { history.back(); })", "Error!");
            model.addAttribute("script", script);
            return "script/sweet";
        }
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

    // 게시판별 목록 조회 - 추가 구현
    @GetMapping("/all/{bNo}")
    public String getAllPostsByBoard(@PathVariable Long bNo, @ModelAttribute PostSearchInfo postSearchInfo,
                                     Model model) {
        Page<Post> pageList = postService.findByBoardWithPage(postSearchInfo,bNo);
        List<Post> postList = pageList.getContent();

        int nowPage = pageList.getPageable().getPageNumber() + 1; // 현재 페이지
        int startPage = (nowPage-1) / 10 * 10 + 1; // 첫페이지
        int endPage = Math.min(startPage + 10 - 1, pageList.getTotalPages()); // 마지막 페이지

        model.addAttribute("postList", postList);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "post/posts";
    }

    // 단일 게시글 조회
    @GetMapping("/detail/{pNo}")
    public String getOnePost(@PathVariable Long pNo, Model model) {
        try {
            Post post = postService.findPostWithView(pNo);
            List<Comment> commentList = commentService.findAllWithPage(pNo);
            CommentRequestDto commentRequestDto = new CommentRequestDto();

            // 작성한 회원인 경우에만 수정/삭제 가능
            if(memberUtil.isLogin()) {
                String nickname = memberUtil.getMember().getMNickName();
                String writer = post.getMember().getMNickName();
                if(nickname.equals(writer)){
                    model.addAttribute("writer",writer);
                }
            }
            /* 댓글 관련 객체 추가*/
            model.addAttribute("commentDto",commentRequestDto);
            model.addAttribute("commentList", commentList);
            model.addAttribute("post", post);
            return "post/detail";
        }catch(Exception e){
            String script = String.format("Swal.fire({title: '%s', text: '%s', icon: 'error'})" +
                    ".then(function() { location.href='/post/create'; })", "Error!", e.getMessage());
            model.addAttribute("script", script);
            return "script/sweet";
        }
    }

    @PostMapping("/detail/{pNo}")
    public String saveComment(@PathVariable Long pNo, @Valid CommentRequestDto commentRequestDto,
                              Errors errors, Model model){
        if(errors.hasErrors()){
            return "post/detail";
        }
        commentService.commentSave(commentRequestDto,pNo);
        return "redirect:/post/detail/"+pNo;
    }

    //삭제
    @DeleteMapping("/{pNo}")
    public String deletePost(@PathVariable Long pNo) {
        postService.deletePost(pNo);
        return "redirect:/post/all";
    }

    /* 게시글 수정 */
    @GetMapping("/{pNo}/edit")
    public String editPost(@PathVariable Long pNo, Model model){
        Post post = postService.findPost(pNo);
        post.decreaseViewCount();
        PostDTO dto = PostDTO.of(post);
        model.addAttribute("post",dto);
        return "post/edit";
    }

    @PutMapping("/{pNo}/edit")
    public String editPostPs(@PathVariable Long pNo, @ModelAttribute("post") @Valid PostDTO updatedPost, Model model) {
        try {
            postService.updatePost(pNo, updatedPost);
            String str = String.valueOf(pNo);
            String script = String.format("Swal.fire({title: '수정 완료!', text: '정상적으로 수정되었습니다.', icon: 'success'})" +
                    ".then(function() { location.href='/post/all'; })", "Success!");
            model.addAttribute("script", script);
            return "script/sweet";
        }catch(Exception e){
            String script = String.format("Swal.fire({title: '%s', text: '%s', icon: 'error'})" +
                    ".then(function() { location.href='/post/create'; })", "Error!", e.getMessage());
            model.addAttribute("script", script);
            return "script/sweet";
        }
    }
}