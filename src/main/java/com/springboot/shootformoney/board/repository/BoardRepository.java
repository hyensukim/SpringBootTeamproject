package com.springboot.shootformoney.board.repository;

import com.springboot.shootformoney.board.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, String> {

    Board findBybNo(Long bNo); // 게시판 bNo 조회

    boolean existsBybName(String bName); // 게시판 이름 중복 여부 판단

    // 게시판 - 게시글 개수
    @Query("SELECT COUNT(p) FROM Board b JOIN b.posts p WHERE b.bNo = :bNo")
    int getPostCountByBoardId(@Param("bNo") Long bNo);

    // 게시판 이름으로 검색
    Optional<Board> findBybName(String bName);

}

