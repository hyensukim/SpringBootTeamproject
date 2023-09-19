package com.springboot.shootformoney.post;


import com.springboot.shootformoney.board.entity.Board;
import com.springboot.shootformoney.common.BaseEntity;
import com.springboot.shootformoney.file.File;
import com.springboot.shootformoney.member.entity.Member;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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



    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "bNo")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval=true)
    private List<File> files = new ArrayList<>();

    @Builder
    public Post(String title, String content) {
        this.pTitle = title;
        this.pContent= content;
    }

    public Post() {

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


    public Long getBNo() {
        return this.board == null ? null : this.board.getBNo();
    }

    public String getBName() {
        return this.board == null ? null : this.board.getBName();
    }

      public void setMember(Member member) {
        this.member= member;
        if (!member.getPosts().contains(this)) {
            member.getPosts().add(this);
       }
      }

    public void addFile(File file) {
        this.files.add(file);
        if (file.getPost() != this) {
            file.setPost(this);
        }
    }

}
