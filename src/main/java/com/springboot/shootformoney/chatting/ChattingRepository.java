package com.springboot.shootformoney.chatting;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface ChattingRepository extends JpaRepository<Chatting, Integer> {
    // 특정 경기에 대한 모든 댓글을 시간 순으로 가져오는 메서드
    List<Chatting> findByMatchOrderByCommentTimeAsc(Game game);

    // 특정 시점 이후의 새로운 댓글들을 가져오는 메서드
    List<Chatting> findByMatchAndCommentTimeAfterOrderByCommentTimeAsc(Game game, LocalDateTime time);
}
