package com.springboot.shootformoney.post;

import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostRepository {

    private final EntityManager em;

    //게시판 저장
    public void save(Post post){
        if (post.getPNo() == null) {
            em.persist(post);
        } else {
            em.merge(post);
        }
    }

    //게시판 삭제
    public void delete(Post post) {
        em.remove(post);
    }


    //게시판 id로 하나 조회
    public Post findOne(Long id){
        return em.find(Post.class, id);
    }

    //게시판 전체 조회
    public List<Post> findAll() {
        return em.createQuery("select p from Post p", Post.class)
                .getResultList();
    }

    //게시글 제목으로 조회
    public List<Post> findByTitle(String title) {
        return em.createQuery("select p from Post p where p.title = :title", Post.class)
                .setParameter("title", title)
                .getResultList();
    }


  //  public List<Post> findByMemberId(String memberId) {
  //      return em.createQuery("select p from Post p where p.member.mId = :memberId", Post.class)
  //              .setParameter("memberId", memberId)
  //              .getResultList();
  //  }

}