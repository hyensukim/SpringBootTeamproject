package com.springboot.shootformoney.board;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BoardRepository {

    private final EntityManager em;

    public void save(Board board){
        if (board.getBNo() == null) {
            em.persist(board);
        } else {
            em.merge(board);
        }
    }

    public void delete(Board board) {
        em.remove(em.contains(board) ? board : em.merge(board));
    }

    public Board findOne(Long id){
        return em.find(Board.class, id);
    }

}
