package com.springboot.shootformoney.comment;

import com.springboot.shootformoney.comment.CommentDto;
import com.springboot.shootformoney.comment.CommentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/{mNo}/{pNo}")
    public String addComment(@PathVariable Integer mNo,
                             @PathVariable Integer pNo,
                             @RequestBody CommentDto commentDto,
                             Model model) {
        try {
            commentService.addComment(mNo, pNo, commentDto.getContent());
            model.addAttribute("pageTitle", "Comment added successfully");

            return "success";  // 'success' 라는 이름의 뷰가 있어야 합니다.
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
            return "error";  // 'error' 라는 이름의 뷰가 있어야 합니다.
        }
    }
}
