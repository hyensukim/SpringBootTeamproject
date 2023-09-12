package com.springboot.shootformoney.restcontroller;

import com.springboot.shootformoney.game.entity.Game;
import com.springboot.shootformoney.game.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//For testing MySQL..
@RestController
public class SQLTestController {
    private final GameRepository gameRepository;

    @Autowired
    public SQLTestController(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @GetMapping("/games")
    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }

    @GetMapping("/games/{league}")
    public List<Game> getGamesByLeague(@PathVariable String league) {
        return gameRepository.findBygLeague(league);
    }
}
