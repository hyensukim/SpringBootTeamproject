package com.springboot.shootformoney.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    @Transactional
    public Long save(Comment comment) {
        commentRepository.save(comment);
        return comment.getCNo();
    } // 댓글 저장

    @Transactional(readOnly = true)
    public Comment findOne(Long id) {
        return commentRepository.findOne(id);
    } // 댓글 단일 조회

    @Transactional(readOnly = true)
    public List<Comment> findAll() {
        return commentRepository.findAll();
    } // 댓글 전체 조회

    @Transactional
    public void delete(Long id) {
        Comment comment = findOne(id);
        if (comment != null) {
            commentRepository.delete(comment);
        } // 댓글 지우기 // 회원 로그인된 세션 정보 불러와서 일치하면 지울수 있게 하기

    }
}