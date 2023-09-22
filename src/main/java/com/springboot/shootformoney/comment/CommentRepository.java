package com.springboot.shootformoney.comment;
import com.springboot.shootformoney.post.PostRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class CommentRepository {

    private final EntityManager em;


    public void save(Comment comment) {
        if (comment.getCNo() == null) {
            em.persist(comment);
        } else {
            em.merge(comment);
        }
    } // 댓글 저장

    public Comment findOne(Long id) {
        return em.find(Comment.class, id);
    } // 댓글 단일 조회

    public List<Comment> findAll() {
        return em.createQuery("select c from Comment c", Comment.class)
                .getResultList();
    } // 댓글 전부 조회

    public void delete(Comment comment) {
        em.remove(comment);
    } // 댓글 지우기


}