package com.springboot.shootformoney.member.repository;

import com.springboot.shootformoney.member.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {

    // 아이디로 회원 조회.
    MemberEntity findByM_id(String m_id);

}
