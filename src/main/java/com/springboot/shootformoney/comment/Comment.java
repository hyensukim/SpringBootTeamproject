package com.springboot.shootformoney.comment;

import com.springboot.shootformoney.member.entity.Member;
import com.springboot.shootformoney.post.Post;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Comment {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long cNo;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="m_no")
    private Member member;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="p_no")
    private Post post;

    private String cContent;

    private LocalDateTime cCreatedAt;

    private LocalDateTime cUpdatedAt;

    // getter and setter methods...
}