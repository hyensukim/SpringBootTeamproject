package com.springboot.shootformoney.bet.repository;

import com.springboot.shootformoney.bet.entity.Bet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BetRepository extends JpaRepository<Bet, Long> {
    Optional<Bet> findByBtNo(Long btNo);
}
