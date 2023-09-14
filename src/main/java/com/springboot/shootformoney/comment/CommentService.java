package com.springboot.shootformoney.comment;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    private final CommentRepository commentRepository;



    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Transactional
    public Comment createComment(CommentCreateDto c_content){
        return commentRepository.save(c_content);
    }

    public List<Comment> getAllComments(){
        return commentRepository.findAll();
    }

    public Optional<Comment> getCommentById(Long id){
        return commentRepository.findById(id);
    }

    @Transactional
    public void deleteById(Long id) {
        if(!commentRepository.existsById(id)){
            throw new IllegalArgumentException("No Comment with given id: " + id);
        }

        commentRepository.deleteById(id);
    }

    @Transactional
    public Comment updateComment(Long id, CommentUpdateDto newContent){
        // Find the existing comment
        Optional<Comment> optionalComment = commentRepository.findById(id);

        if (optionalComment.isPresent()) {
            // Update the content of the existing comment
            Comment existingComment = optionalComment.get();
            existingComment.setCContent(newContent);

            // Save and return the updated comment
            return commentRepository.save(existingComment);
        } else {
            throw new IllegalArgumentException("No Comment with given id: " + id);
        }
    }
}