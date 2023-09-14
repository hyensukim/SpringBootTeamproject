package com.springboot.shootformoney.game.controllers;

import com.springboot.shootformoney.game.entity.Game;
import com.springboot.shootformoney.game.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/*
* 경기 정보를 Get하는 Controller.
* Author: Hyedokal(https://www.github.com/Hyedokal)
*/
@RestController
@RequestMapping("/list")
public class MatchController {

    private final MatchService matchService;

    @Autowired
    public MatchController(MatchService matchService){
        this.matchService = matchService;
    }
    @GetMapping("/unstarted/epl")
    @ResponseBody
    public List<Game> unstartedPLMatches(){

        return matchService.getUnstartedPLMatches();
    }

    @GetMapping("/unstarted/laliga")
    @ResponseBody
    public List<Game> unstartedPDMatches(){

        return matchService.getUnstartedPDMatches();
    }
    @GetMapping("/unstarted/bundes")
    @ResponseBody
    public List<Game> unstartedBL1Matches(){

        return matchService.getUnstartedBL1Matches();
    }
    @GetMapping("/unstarted/entirelist")
    @ResponseBody
    public List<Game> everyUnstartedMatches(){

        return matchService.getAllUnstartedMatches();
    }

    @GetMapping("/finished/epl")
    @ResponseBody
    public List<Game> finishedPLMatches(){

        return matchService.getFinishedPLMatches();
    }

    @GetMapping("/finished/laliga")
    @ResponseBody
    public List<Game> finishedPDMatches(){

        return matchService.getFinishedPDMatches();
    }

    @GetMapping("/finished/bundes")
    @ResponseBody
    public List<Game> finishedBL1Matches(){

        return matchService.getFinishedBL1Matches();
    }

    @GetMapping("/finished/entirelist")
    @ResponseBody
    public List<Game> everyFinishedMatches(){

        return matchService.getAllFinishedMatches();
    }

    @GetMapping("/{matchId}")
    public ResponseEntity<Game> getGameByMatchId(@PathVariable Integer matchId) {
        Optional<Game> game = matchService.getGameByMatchId(matchId);

        if(game.isPresent()) {
            return ResponseEntity.ok(game.get());
        } else {
            return ResponseEntity.notFound().build();
        }

    }
}
