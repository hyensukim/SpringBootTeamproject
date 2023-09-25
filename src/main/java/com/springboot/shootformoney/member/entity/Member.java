package com.springboot.shootformoney.member.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.springboot.shootformoney.common.BaseEntity;
import com.springboot.shootformoney.member.enum_.Grade;
import com.springboot.shootformoney.member.enum_.Role;
import com.springboot.shootformoney.post.Post;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@NoArgsConstructor @AllArgsConstructor
@Table(indexes={
        @Index(name="idx_member_name", columnList = "m_name"),
        @Index(name="idx_member_email", columnList = "m_email"),
        @Index(name="idx_member_nickName", columnList= "m_nickName")
})
public class Member extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mNo;

    @Column(name = "m_id",length = 40, nullable = false, unique = true)
    private String mId;

    @Column(name = "m_password", length = 65, nullable = false)
    private String mPassword;

    @Column(name = "m_name",length = 40, nullable = false)
    private String mName;

    @Column(name = "m_phone",length=11, nullable = false)
    private String mPhone;

    @Column(name = "m_email",length = 100, nullable = false)
    private String mEmail;

    @Column(name = "m_nickName",length = 40, nullable = false)
    private String mNickName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role = Role.MEMBER;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Grade grade = Grade.CONFERENCE;

    @Column(name = "m_level",nullable = false)
    private Integer mLevel = 1;

    @Column(name = "m_stack")
    private Long mStack = 0L;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="l_no", referencedColumnName = "l_No")
    private LoginData loginData;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "eNo", referencedColumnName = "eNo")
    private Euro euro;

    public void createEuro() {
        this.euro = new Euro();
        euro.setMember(this);  // 만약 양방향 매핑시, 해당 코드 필요
    }
}
