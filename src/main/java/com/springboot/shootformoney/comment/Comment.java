package com.springboot.shootformoney.comment;

import com.springboot.shootformoney.common.BaseEntity;
import com.springboot.shootformoney.member.entity.Member;
import com.springboot.shootformoney.post.Post;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Entity
@Getter
@Setter
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "c_no")
    private Long cNo;

    @Column(name = "c_content")
    private String cContent;  // 댓글 본문

    @Column(name = "m_nick_name")
    private String mNickName; // 회원 별명

//    @Column(name = "p_no")
//    private Long pNo; // 게시글 번호


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "m_no")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "p_no", insertable=false, updatable=false)
    private Post post;
}