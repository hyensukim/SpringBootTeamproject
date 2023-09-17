package com.springboot.shootformoney.member.repository;

import com.springboot.shootformoney.member.entity.Member;
import com.springboot.shootformoney.post.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Post2Repository extends JpaRepository<Post,Long> {
}
