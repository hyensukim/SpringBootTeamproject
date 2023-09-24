package com.springboot.shootformoney.comment;

import com.springboot.shootformoney.comment.dto.CommentResponseDto;
import com.springboot.shootformoney.member.utils.MemberUtil;
import com.springboot.shootformoney.post.PostResponseDto;
import com.springboot.shootformoney.post.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * CSR : Client Side Rendering code
 */
@Controller
@RequiredArgsConstructor
public class PostIndexController {

    private final PostService postService;
    private final MemberUtil memberUtil;

    @GetMapping("/post/read/{pNo}")
    public String read(@PathVariable Long pNo, Model model){
        PostResponseDto dto = postService.findById(pNo);
        List<CommentResponseDto> comments = dto.getComments();

        /* 댓글 관련 */
        if(comments != null){
            model.addAttribute("comments",comments);
        }

        /* 사용자 관련 */
        if(memberUtil.isLogin()){
            model.addAttribute("member", memberUtil.getMember());

            // 게시글 작성자 본인인지 확인
            if(dto.getMNo().equals(memberUtil.getMember().getMNo())){
                model.addAttribute("mNickName",true);
            }
        }

        // 조회수 증가
        postService.updateView(pNo);

        model.addAttribute("post",dto);
        return "post/read";
    }
}
