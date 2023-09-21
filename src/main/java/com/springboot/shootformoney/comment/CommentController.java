package com.springboot.shootformoney.comment;

import com.springboot.shootformoney.post.Post;
import com.springboot.shootformoney.post.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;
    private final PostService postService;
    private Post post;


    public CommentController(CommentService commentService, PostService postService) {
        this.commentService = commentService;
        this.postService = postService;
    }

    @GetMapping("/post/{pNo}")
    public String getCommentsByPost(@PathVariable Long pNo, Model model) {
        List<Comment> comments = commentService.findCommentsByPost(pNo);
        model.addAttribute("comments", comments);
        return "comment/list"; // Adjust the view name as needed
    }

    @GetMapping("/member/{mNo}")
    public String getCommentsByMember(@PathVariable Long mNo, Model model) {
        List<Comment> comments = commentService.findCommentsByMember(mNo);
        model.addAttribute("comments", comments);
        return "comment/list"; // Adjust the view name as needed
    }
}