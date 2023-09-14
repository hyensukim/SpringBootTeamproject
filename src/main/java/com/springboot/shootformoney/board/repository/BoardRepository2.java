//package com.springboot.shootformoney.board.repository;
//
//import com.springboot.shootformoney.board.entity.Board;
//import jakarta.persistence.EntityManager;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//@Repository
//@RequiredArgsConstructor
//public class BoardRepository implements JpaRepository<Board, String> {
//
//    private final EntityManager em;
//
//    public void save(Board board){
//        if (board.getBNo() == null) {
//            em.persist(board);
//        } else {
//            em.merge(board);
//        }
//    }
//
//    public void delete(Board board) {
//        em.remove(em.contains(board) ? board : em.merge(board));
//    }
//
//    public Board findOne(Long bNo){
//        return em.find(Board.class, bNo);
//    }
//
//}
