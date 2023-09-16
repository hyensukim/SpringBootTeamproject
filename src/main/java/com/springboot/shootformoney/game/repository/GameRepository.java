package com.springboot.shootformoney.game.repository;

import com.springboot.shootformoney.game.dto.data.Result;
import com.springboot.shootformoney.game.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
    Optional<Game> findByMatchId(Integer matchId);

    Boolean existsByMatchId(Integer matchId);

    @Query("SELECT g FROM Game g WHERE g.result = 'NN' ORDER BY g.gStartTime")
    List<Game> findAllUnstartedMatches();

    @Query("SELECT g FROM Game g WHERE g.result = 'NN' AND g.gLeague = 'Bundesliga' ORDER BY g.gStartTime")
    List<Game> findUnstartedBL1Matches();

    @Query("SELECT g FROM Game g WHERE g.result = 'NN' AND g.gLeague = 'Premier League' ORDER BY g.gStartTime")
    List<Game> findUnstartedPLMatches();

    @Query("SELECT g FROM Game g WHERE g.result = 'NN' AND g.gLeague = 'Primera Division' ORDER BY g.gStartTime")
    List<Game> findUnstartedPDMatches();

    @Query("SELECT g FROM Game g WHERE g.result != 'NN' AND g.gLeague = 'Premier League' ORDER BY g.gStartTime DESC")
    List<Game> findFinishedPLMatches();

    @Query("SELECT g FROM Game g WHERE g.result != 'NN' AND g.gLeague = 'Primera Division' ORDER BY g.gStartTime DESC")
    List<Game> findFinishedPDMatches();

    @Query("SELECT g FROM Game g WHERE g.result != 'NN' AND g.gLeague = 'Bundesliga' ORDER BY g.gStartTime DESC")
    List<Game> findFinishedBL1Matches();

    @Query("SELECT g FROM Game g WHERE g.result != 'NN' ORDER BY g.gStartTime DESC")
    List<Game> findAllFinishedMatches();

}
