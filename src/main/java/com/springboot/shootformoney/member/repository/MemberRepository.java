package com.springboot.shootformoney.member.repository;

import com.springboot.shootformoney.member.entity.Member;
import com.springboot.shootformoney.member.enum_.Grade;
import com.springboot.shootformoney.member.enum_.Role;
import com.springboot.shootformoney.post.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long>, QuerydslPredicateExecutor<Member>{

    Member findBymId(String mId); // 회원 Id로 조회.
    Member findBymNickName(String mNickName); // 닉네임으로 조회
    Member findBymPhone(String mPhone); // 전화 번호로 조회
    Member findBymEmail(String mEmail); // 이메일로 조회

//    Member findBymName(String mName); // 회원 이름 조회
//    List<Member> findBymLevel(Integer mLevel); // 회원 LV 조회
//    List<Member> findBymNo(Long mNo); // 회원 no로 조회
//    List<Member> findByGrade(Grade grade); // 회원 등급 조회
//    List<Member> findByRole(Role role); // 회원 권한 조회

    // 윤환 추가 검색 * 페이징 처리
//    Page<Member> findBymNo(
//            Long mNo, String mId,
//            String mName, String mNickName,
//            Grade grade, Integer mLevel,
//            Role role, Pageable pageable);
    Page<Member> findBymNo(Long mNo, Pageable pageable); // 회원 no로 조회
    Page<Member> findBymId(String mName, Pageable pageable); // 회원 ID 조회
    Page<Member> findBymName(String mName, Pageable pageable); // 회원 이름 조회
    Page<Member> findBymNickName(String mName, Pageable pageable); // 회원 별명 조회
    Page<Member> findBymLevel(Integer mLevel, Pageable pageable); // 회원 LV 조회
    Page<Member> findByGrade(Grade grade, Pageable pageable); // 회원 등급 조회
    Page<Member> findByRole(Role role, Pageable pageable); // 회원 권한 조회

    @Query("SELECT m FROM Member m WHERE m.mEmail = :mEmail AND m.mName = :mName ")
    Member findBymEmailAndmName(String mEmail, String mName); // 이메일 + 이름으로 조회
    @Query("SELECT m FROM Member m WHERE m.mId = :mId AND m.mEmail = :mEmail ")
    Member findBymIdAndmEmail(String mId, String mEmail); // 아이디 + 이메일로 조회
    @Query("SELECT m FROM Member m ORDER BY m.mLevel DESC, m.mStack DESC")
    List<Member> findByMemberOrderBymLevelAAndMStack();
}   
