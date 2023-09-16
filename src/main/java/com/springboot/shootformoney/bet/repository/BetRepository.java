package com.springboot.shootformoney.bet.repository;

import com.springboot.shootformoney.bet.entity.Bet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BetRepository extends JpaRepository<Bet, Long> {
    Optional<Bet> findByBtNo(Long btNo);

    //회원번호로 배팅내역 조회하기.
    @Query("SELECT b FROM Bet b WHERE b.member.mNo = :mNo ORDER BY b.btTime DESC")
    List<Bet> findBymNo(@Param("mNo") Long mNo);
}
