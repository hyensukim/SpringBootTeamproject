package com.springboot.shootformoney.comment.repository;

import com.springboot.shootformoney.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface CommentRepository extends JpaRepository<Comment,Long>, QuerydslPredicateExecutor<Comment> {

}
