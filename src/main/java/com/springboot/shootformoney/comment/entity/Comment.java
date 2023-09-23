package com.springboot.shootformoney.comment.entity;

import com.springboot.shootformoney.common.BaseEntity;
import com.springboot.shootformoney.member.entity.Member;
import com.springboot.shootformoney.post.Post;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Comment extends BaseEntity{

    @Id
    @GeneratedValue
    @Column(name = "c_no")
    private Long cNo;

    @Column(name = "c_content")
    private String cContent;  // 댓글 본문

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mNo")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "p_no", insertable=false, updatable=false)
    private Post post;
}