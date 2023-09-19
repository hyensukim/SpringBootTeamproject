package com.springboot.shootformoney.board.repository;

import com.springboot.shootformoney.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, String> {

    Board findBybNo(Long bNo); // 게시판 bNo 조회

    Board findBybName(String bName); // 게시판 제목별 조회

    boolean existsBybName(String bName); // 게시판 이름 중복 여부 판단

//    Page<Board> findByBNo(Long bNo, Pageable pageable);
    int countBybNo(Long bNo);





}
