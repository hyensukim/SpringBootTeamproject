package com.springboot.shootformoney.game.repository;

import com.springboot.shootformoney.game.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
    List<Game> findBygLeague(String gLeague);
    Optional<Game> findByMatchId(Integer matchId);

    @Query("SELECT g FROM Game g WHERE g.result = 'NN'")
    List<Game> findAllUnstartedMatches();

    @Query("SELECT g FROM Game g WHERE g.result = 'NN' AND g.gLeague = 'Bundesliga'")
    List<Game> findUnstartedBL1Matches();

    @Query("SELECT g FROM Game g WHERE g.result = 'NN' AND g.gLeague = 'Premier League'")
    List<Game> findUnstartedPLMatches();

    @Query("SELECT g FROM Game g WHERE g.result = 'NN' AND g.gLeague = 'Primera Division'")
    List<Game> findUnstartedPDMatches();



    @Query("SELECT g FROM Game g WHERE g.result != 'NN' AND g.gLeague = 'Premier League'")
    List<Game> findFinishedPLMatches();

    @Query("SELECT g FROM Game g WHERE g.result != 'NN' AND g.gLeague = 'Primera Division'")
    List<Game> findFinishedPDMatches();

    @Query("SELECT g FROM Game g WHERE g.result != 'NN' AND g.gLeague = 'Bundesliga'")
    List<Game> findFinishedBL1Matches();

    @Query("SELECT g FROM Game g WHERE g.result != 'NN'")
    List<Game> findAllFinishedMatches();



}
