package com.springboot.shootformoney.member;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Member {

    @Id
    @GeneratedValue
    private Long m_no;

    private String m_id;

    private String m_password;

    private String m_name;

    private String m_phone;

    private String m_email;

    @Embedded
    MemberInfo memberInfo;
}
