package com.springboot.shootformoney.post;

import com.springboot.shootformoney.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.QueryByExampleExecutor;

public interface PagingRepository extends JpaRepository<Post,Long>, QuerydslPredicateExecutor<Post> {
}
