package com.springboot.shootformoney.post;


import com.springboot.shootformoney.board.entity.Board;
import com.springboot.shootformoney.board.repository.BoardRepository;
import com.springboot.shootformoney.member.entity.Member;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostRepository {

    private final EntityManager em;
    private final BoardRepository boardRepository;

    //게시판 저장
    public void save(Post post, Long bNo){
        if (post.getPNo() == null) {
            Board board = boardRepository.findBybNo(bNo);
            if (board == null) {
                throw new IllegalArgumentException("해당 번호의 게시판이 존재하지 않습니다: " + bNo);
            }
            post.setBoard(board);
            em.persist(post);
        } else {
            em.merge(post);
        }
    }

    //게시글 삭제
    public void delete(Post post) {
        em.remove(post);
    }


    //게시판 id로 하나 조회
//    public Post findOne(Long pNo){
//        return em.find(Post.class, pNo);
//    }

    public Post findOne(Long pNo){
        return em.createQuery("select p from Post p join fetch p.board where p.pNo = :pNo", Post.class)
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

    public List<Post> findByMemberId(String memberId) {
        return em.createQuery("select p from Post p where p.member.mId = :memberId", Post.class)
                .setParameter("memberId", memberId)
                .getResultList();
    }



}