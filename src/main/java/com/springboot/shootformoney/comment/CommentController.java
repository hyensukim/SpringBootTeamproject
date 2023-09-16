package com.springboot.shootformoney.comment;

import com.springboot.shootformoney.comment.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/{mNo}/{pNo}")
    public ResponseEntity<Void> addComment(@PathVariable Integer mNo,
                                           @PathVariable Integer pNo,
                                           @RequestBody CommentDto commentDto) {
        try {
            commentService.addComment(mNo, pNo, commentDto.getContent());
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
