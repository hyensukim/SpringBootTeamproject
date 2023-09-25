package com.springboot.shootformoney.comment.controller;


import com.springboot.shootformoney.comment.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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