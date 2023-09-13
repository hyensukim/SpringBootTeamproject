package com.springboot.shootformoney.board.repository;

import com.springboot.shootformoney.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, String> {

    Board findBybNo(Long bNo); // 게시글 bNo 조회

}
