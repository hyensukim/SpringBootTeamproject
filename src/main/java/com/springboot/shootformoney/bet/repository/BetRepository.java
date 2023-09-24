package com.springboot.shootformoney.bet.repository;

import com.springboot.shootformoney.bet.entity.Bet;
import com.springboot.shootformoney.game.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BetRepository extends JpaRepository<Bet, Long>, QuerydslPredicateExecutor<Bet> {
    Optional<Bet> findByBtNo(Long btNo);

    //회원번호로 배팅내역 조회하기.
    @Query("SELECT b FROM Bet b WHERE b.member.mNo = :mNo ORDER BY b.btTime DESC")
    List<Bet> findBymNo(@Param("mNo") Long mNo);

    //배팅 적중한 사람 조회하기.(중복 체크 포함)
    @Query("SELECT b FROM Bet b WHERE b.game = :game AND b.game.result = b.expect AND b.endPaid = 0")
    List<Bet> findByResultAndExpect(@Param("game") Game game);

    //경기가 끝난 배팅 중, 정산 안된 배팅 조회하기
    @Query("SELECT b FROM Bet b WHERE b.game = :game AND b.endPaid = 0")
    List<Bet> findByGameAndEndPaid(@Param("game") Game game);

    //gNo로 배팅내역 조회하기.
    @Query("SELECT b FROM Bet b WHERE b.game.gNo = :gNo")
    List<Bet> findAllByGame_gNo(@Param("gNo") Long gNo);

}
