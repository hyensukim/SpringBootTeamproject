package com.springboot.shootformoney.member.repository;

import com.springboot.shootformoney.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findBymId(String mId); // 회원 Id로 조회.
    Member findBymNickName(String mNickName); // 닉네임으로 조회
    Member findBymPhone(String mPhone); // 전화 번호로 조회
    Member findBymEmail(String mEmail); // 이메일로 조회

    @Query("SELECT m FROM Member m WHERE m.mEmail = :mEmail AND m.mName = :mName ")
    Member findBymEmailAndmName(String mEmail, String mName); // 이메일 + 이름으로 조회
    @Query("SELECT m FROM Member m WHERE m.mId = :mId AND m.mEmail = :mEmail ")
    Member findBymIdAndmEmail(String mId, String mEmail); // 아이디 + 이메일로 조회

}   
