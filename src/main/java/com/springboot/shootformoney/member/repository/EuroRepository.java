package com.springboot.shootformoney.member.repository;

import com.springboot.shootformoney.member.entity.Euro;
import com.springboot.shootformoney.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface EuroRepository extends JpaRepository<Euro, Long> {
    Euro findByMember(Member member);

    @Query("SELECT e FROM Euro e WHERE e.member.mNo = :mNo")
    Euro findBymNo(@Param("mNo") Long mNo);
}
