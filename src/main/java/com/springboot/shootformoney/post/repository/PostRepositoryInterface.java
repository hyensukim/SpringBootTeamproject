package com.springboot.shootformoney.post.repository;

import com.springboot.shootformoney.post.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepositoryInterface extends JpaRepository<Post,Long>, QuerydslPredicateExecutor<Post> {

    /*
    페이징 처리용 by 유난
     */
    Page<Post> findBypNo(Long pNo, Pageable pageable); // 게시글 no로 조회
    @Query("SELECT p FROM Post p WHERE p.member.mId = :mId")
    Page<Post> findByMembermId(String mId, Pageable pageable); // 회원 ID 조회
    @Query("SELECT p FROM Post p WHERE p.member.mNickName = :mNickName")
    Page<Post> findByMembermNickName(String mNickName, Pageable pageable); // 회원 별명 조회
    @Query("SELECT p FROM Post p WHERE p.board.bName = :bName")
    Page<Post> findByBoardbName(String bName, Pageable pageable); // 게시판 명 조회
    Page<Post> findBypTitle(String pTile, Pageable pageable); // 게시판 제목 조회





}
