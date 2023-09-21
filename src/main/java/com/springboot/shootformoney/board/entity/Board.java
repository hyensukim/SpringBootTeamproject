package com.springboot.shootformoney.board.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.springboot.shootformoney.post.Post;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Board {

    @Id
    @GeneratedValue
    @Column(name = "b_no")
    private Long bNo;

    @Column(name = "b_name")
    private String bName; // 게시판 명(카테고리 명)

    @Column(name = "b_pageNo")
    @ColumnDefault("10")
    private int bPageNo; // 페이지 수(하단)

    @Column(name = "b_unitNo")
    @ColumnDefault("15")
    private int bUnitNo; // 리스트별 게시글 개수

    @Column(name = "b_isFile")
    @ColumnDefault("false")
    private boolean bIsFile; // 파일 작성 여부

//    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    @JsonIgnore // 클라이언트로부터 게시판 정보만 받고 게시물 정보는 따로 처리하려는 경우에 유용
//    @OneToMany(mappedBy = "parent")
    private List<Post> posts = new ArrayList<>();


}