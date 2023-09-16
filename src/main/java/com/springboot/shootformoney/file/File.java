package com.springboot.shootformoney.file;

import com.springboot.shootformoney.post.Post;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter @Setter
@Table(name = "files")
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "f_no")
    private Integer fNo;

    @Column(name = "f_name")
    private String fName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "p_no")
    private Post post;

    @Column(name = "f_path")
    private String fPath;

    // 생성자, getter, setter 등 필요한 코드 생략

}
