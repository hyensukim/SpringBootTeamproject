package com.springboot.shootformoney.post;


import com.springboot.shootformoney.board.entity.Board;
import com.springboot.shootformoney.comment.entity.Comment;
import com.springboot.shootformoney.common.BaseEntity;
import com.springboot.shootformoney.member.entity.Member;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
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
    @JoinColumn(name = "mNo")
    private Member member;

    @Builder
    public Post(String title, String content) {
        this.pTitle = title;
        this.pContent= content;
    }

    @OneToMany(mappedBy = "post", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @OrderBy("createdAt desc")//댓글 관련(정렬)
    private List<Comment> comments;

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

}
