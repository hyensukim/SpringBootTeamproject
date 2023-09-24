package com.springboot.shootformoney.comment;


import com.springboot.shootformoney.comment.entity.Comment;
import com.springboot.shootformoney.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@Controller
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

//    @PostMapping("/")
//    public String createComment(@ModelAttribute Comment comment) {
//        commentService.save(comment);
//        return "redirect:/comments";
//    } // 댓글 달기


    @GetMapping("/")
    public String getAllComments(Model model) {
        List<Comment> comments = commentService.findAll();
        model.addAttribute("comments", comments);
        return "commentsList";
    } // 댓글 전부 조회

    @PostMapping("/{id}/delete")
    public String deleteComment(@PathVariable Long id) {
        commentService.delete(id);
        return "redirect:/comments";
    } // 댓글 지우기
}