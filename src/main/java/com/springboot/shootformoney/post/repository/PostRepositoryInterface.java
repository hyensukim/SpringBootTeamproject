package com.springboot.shootformoney.post.repository;

import com.springboot.shootformoney.post.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import java.util.List;
import java.util.Optional;

public interface PostRepositoryInterface extends JpaRepository<Post,Long>, QuerydslPredicateExecutor<Post> {

//    Page<Post> findByPTitleContaining(String query, Pageable pageable);

//    Page<Post> findBypNo(Long pNo, Pageable pageable);
//
//    Page<Post> findByBoardbName(String bName, Pageable pageable);
//
//    Page<Post> findByMembermId(String mId, Pageable pageable);
//
//    Page<Post> findByMembermNickName(String mNickName, Pageable pageable);
//
//    Page<Post> findBypTitleContaining(String pTitle, Pageable pageable);
}
