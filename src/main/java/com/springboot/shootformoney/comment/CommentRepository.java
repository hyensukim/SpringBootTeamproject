package com.springboot.shootformoney.comment;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CommentRepository {

    private final EntityManager em;

    public CommentRepository(EntityManager em) {
        this.em = em;
    }

    public void save(Comment comment) {
        em.persist(comment);
    }

    public void delete(Comment comment) {
        em.remove(comment);
    }

    public Comment findOne(Long cNo) {
        return em.find(Comment.class, cNo);
    }

    public List<Comment> findByPost_PNo(Long pNo) {
        TypedQuery<Comment> query = em.createQuery("SELECT c FROM Comment c WHERE c.post.pNo = :pNo", Comment.class);
        query.setParameter("pNo", pNo);
        return query.getResultList();
    }

    public List<Comment> findByMember_MNo(Long mNo) {
        TypedQuery<Comment> query = em.createQuery("SELECT c FROM Comment c WHERE c.member.mNo = :mNo", Comment.class);
        query.setParameter("mNo", mNo);
        return query.getResultList();
    }

}