package com.springboot.shootformoney.post.repository;

import com.springboot.shootformoney.post.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepositoryInterface extends JpaRepository<Post, Long> {

    Page<Post> findAll(Pageable pageable);
}
