package com.springboot.shootformoney.member;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Embeddable
public class MemberInfo {

    private String m_nickname;

    @Enumerated(EnumType.STRING)
    private MemberGrade memberGrade;

    @Enumerated(EnumType.STRING)
    private MemberRole memberRole;

    private int m_level;
}
