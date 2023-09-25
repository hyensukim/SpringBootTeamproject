package com.springboot.shootformoney.comment.controller;


import com.springboot.shootformoney.comment.dto.CommentRequestDto;
import com.springboot.shootformoney.comment.dto.CommentSearchInfo;
import com.springboot.shootformoney.comment.entity.Comment;
import com.springboot.shootformoney.comment.service.CommentService;
import com.springboot.shootformoney.post.Post;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.StringTokenizer;

@Controller
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final HttpServletRequest request;

    // 댓글 삭제 - view에서 받기
    @DeleteMapping("/{cNo}/delete")
    public String deleteComment(@PathVariable Long cNo) {
        Long pNo = commentService.delete(cNo);
        return "redirect:/post/detail/"+pNo;
    } // 댓글 지우기
}