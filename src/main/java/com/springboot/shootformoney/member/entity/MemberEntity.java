package com.springboot.shootformoney.member.entity;

import com.springboot.shootformoney.common.BaseEntity;
import com.springboot.shootformoney.member.enum_.Grade;
import com.springboot.shootformoney.member.enum_.Role;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Builder
@NoArgsConstructor @AllArgsConstructor
@Table(indexes={
        @Index(name="idx_m_name", columnList = "m_name"),
        @Index(name="idx_m_email", columnList = "m_email")
})
public class MemberEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long m_no;

    @Column(length = 40, nullable = false, unique = true)
    private String m_id;

    @Column(length = 65, nullable = false)
    private String m_password;

    @Column(length = 40, nullable = false)
    private String m_name;

    @Column(length=11, nullable = false)
    private String m_phone;

    @Column(length = 100, nullable = false)
    private String m_email;

    @Column(length = 40, nullable = false)
    private String m_nickName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @ColumnDefault("MEMBER")
    private Role role = Role.MEMBER;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @ColumnDefault("CONFERENCE")
    private Grade grade = Grade.CONFERENCE;

    @Column(nullable = false)
    @ColumnDefault("1")
    private Integer m_level = 1;
}
