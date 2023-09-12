package com.springboot.shootformoney.post;

import com.springboot.shootformoney.board.Board;
import com.springboot.shootformoney.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter

public class Post extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "p_no")
    private Long pNo; // 게시글 번호

    @Column(name = "p_title")
    private String pTitle; // 게시즐 제목

    @Column(name = "p_content")
    private String pContent; // 게시글 내용

    private Long view = 0L; // 조회수


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bNo")
    private Board board;

    //@ManyToOne(fetch = FetchType.LAZY)
    //@JoinColumn(name = "member_id")
    //private MemberEntity member;

    @Builder
    public Post(String title, String content) {
        this.pTitle = title;
        this.pContent= content;
    }

    public void update(String title, String content) {
        this.pTitle = title;
        this.pContent = content;
        this.setUpdatedAt(LocalDateTime.now());
    }

    public void incrementViewCount() {
        this.view += 1;
    }

    public void setBoard(Board board) {
        this.board = board;
        if (!board.getPosts().contains(this)) {
            board.getPosts().add(this);
        }
    }

    //  public void setMember(MemberEntity member) {
    //    this.member= member;
     //   if (!member.getPosts().contains(this)) {
     //       member.getPosts().add(this);
    //   }
  //    }

}
