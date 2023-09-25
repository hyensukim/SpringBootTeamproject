package com.springboot.shootformoney.post;


import com.springboot.shootformoney.board.entity.Board;
import com.springboot.shootformoney.board.repository.BoardRepository;
import com.springboot.shootformoney.member.repository.MemberRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostRepository {

    private final EntityManager em;
    private final BoardRepository boardRepository;


    //게시판 저장
    public void save(Post post){
        if (post.getPNo() == null) {
            em.persist(post);
        } else {
            em.merge(post);
        }
    }

    //게시글 삭제
    public void delete(Post post) {
        em.remove(post);
    }





    public Post findOne(Long pNo){
        return em.createQuery("select p from Post p where p.pNo = :pNo", Post.class)
                .setParameter("pNo", pNo)
                .getSingleResult();
    }



    //게시글 전체 조회
    public List<Post> findAll() {
        return em.createQuery("select p from Post p", Post.class)
                .getResultList();
    }

    //게시글 제목으로 조회
    public List<Post> findByTitle(String pTitle) {
        return em.createQuery("select p from Post p where p.pTitle = :pTitle", Post.class)
                .setParameter("pTitle", pTitle)
                .getResultList();
    }

    public List<Post> findByBoard_BName(String bName) {
        return em.createQuery("select p from Post p where p.board.bName = :bName", Post.class)
                .setParameter("bName", bName)
                .getResultList();
    }

    public List<Post> findAllWithBoard() {
        return em.createQuery("select p from Post p join fetch p.board", Post.class)
                .getResultList();
    }

    // 회원 닉네임(mNickName)에 해당하는 게시글을 조회
    public List<Post> findByMember_MNickName(String mNickName) {
        return em.createQuery("select p from Post p where p.member.mNickName = :mNickName", Post.class)
                .setParameter("mNickName", mNickName)
                .getResultList();
    }

    public List<Post> findByMemberId(String mId) {
        return em.createQuery("select p from Post p where p.member.mId = :mId", Post.class)
                .setParameter("mId", mId)
                .getResultList();
    }

    public List<Post> findByMemberNo(Long mNo) {
        return em.createQuery("select p from Post p where p.member.mNo = :mNo", Post.class)
                .setParameter("mNo", mNo)
                .getResultList();
    }

    public List<Post> findByBoardBNo(Long bNo) {
        return em.createQuery("select p from Post p where p.board.bNo = :bNo", Post.class)
                .setParameter("bNo", bNo)
                .getResultList();
    }

    public List<Post> findAllWithMember() { //수정
        return em.createQuery("SELECT p FROM Post p LEFT JOIN FETCH p.member", Post.class)
                .getResultList();
    }

//    public List<Post> searchPostsByTitle(String sOpt, String sKey) {
//
//        String sOpt = PostSearchInfo.getSOpt();
//        String sKey = PostSearchInfo.getSKey();
//
//        // 제목에 키워드가 포함된 게시물 검색
//        return em.createQuery("select p from Post p where p.pTitle like :title", Post.class)
//                .setParameter("title", "%" + title + "%")
//                .getResultList();
//    }

}