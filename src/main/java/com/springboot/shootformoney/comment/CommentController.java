package com.springboot.shootformoney.comment;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<CommentResponseDto> createComment(@RequestBody CommentCreateDto dto){
        Comment newComment = commentService.createComment(dto);
        // Convert the created entity to a DTO.
        CommentResponseDto responseDto = new CommentResponseDto(newComment);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CommentResponseDto>> getAllComments() {
        List<Comment> comments = commentService.getAllComments();
        // Convert each entity in the list to a DTO.
        List<CommentResponseDto> responseDtos = comments.stream()
                .map(CommentResponseDto::new)
                .collect(Collectors.toList());
        return new ResponseEntity<>(responseDtos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentResponseDto> getOne(@PathVariable Integer id) {
        Optional<Comment> optional = commentService.getCommentById(id);

        if (!optional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Convert the found entity to a DTO.
        CommentResponseDto responseDto = new CommentResponseDto(optional.get());
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateOne(@PathVariable Integer id, @RequestBody CommentUpdateDto dto) {
        try{
            commentService.updateComment(id,dto);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch(IllegalArgumentException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOne(@PathVariable Integer id) {
        try{
            commentService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch(IllegalArgumentException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}