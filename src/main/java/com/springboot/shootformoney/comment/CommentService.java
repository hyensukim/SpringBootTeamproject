package com.springboot.shootformoney.comment;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Transactional
    public Long saveComment(Comment comment) {
        commentRepository.save(comment);
        return comment.getCNo();
    }

    public List<Comment> findCommentsByPost(Long pNo) {
        return commentRepository.findByPost_PNo(pNo);
    }

    public List<Comment> findCommentsByMember(Long mNo) {
        return commentRepository.findByMember_MNo(mNo);
    }
}