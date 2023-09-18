package com.springboot.shootformoney.game.controllers;

import com.springboot.shootformoney.game.entity.Game;
import com.springboot.shootformoney.game.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/*
* 경기 정보를 Get하는 Controller.
* Author: Hyedokal(https://www.github.com/Hyedokal)
*/
@Controller
@RequestMapping("/list")
public class MatchController {

    private final MatchService matchService;

    @Autowired
    public MatchController(MatchService matchService){
        this.matchService = matchService;
    }
    @GetMapping("/unstarted/epl")
    @ResponseBody
    public List<Game> unstartedPLMatches(Model model){
        model.addAttribute("pageTitle", "EPL 경기 목록");
        return matchService.getUnstartedPLMatches();
    }

    @GetMapping("/unstarted/laliga")
    @ResponseBody
    public List<Game> unstartedPDMatches(Model model){
        model.addAttribute("pageTitle", "라리가 경기 목록");
        return matchService.getUnstartedPDMatches();
    }
    @GetMapping("/unstarted/bundes")
    @ResponseBody
    public List<Game> unstartedBL1Matches(Model model){
        model.addAttribute("pageTitle", "분데스 경기 목록");
        return matchService.getUnstartedBL1Matches();
    }
    @GetMapping("/unstarted/entirelist")
    @ResponseBody
    public List<Game> everyUnstartedMatches(Model model){
        model.addAttribute("pageTitle", "전체 경기 목록");
        return matchService.getAllUnstartedMatches();
    }

    @GetMapping("/finished/epl")
    @ResponseBody
    public List<Game> finishedPLMatches(Model model){
        model.addAttribute("pageTitle", "종료된 EPL 경기 목록");
        return matchService.getFinishedPLMatches();
    }

    @GetMapping("/finished/laliga")
    @ResponseBody
    public List<Game> finishedPDMatches(Model model){
        model.addAttribute("pageTitle", "종료된 라리가 경기 목록");
        return matchService.getFinishedPDMatches();
    }

    @GetMapping("/finished/bundes")
    @ResponseBody
    public List<Game> finishedBL1Matches(Model model){
        model.addAttribute("pageTitle", "종료된 분데스 경기 목록");
        return matchService.getFinishedBL1Matches();
    }

    @GetMapping("/finished/entirelist")
    @ResponseBody
    public List<Game> everyFinishedMatches(Model model){
        model.addAttribute("pageTitle", "종료된 경기 목록");
        return matchService.getAllFinishedMatches();
    }

    @GetMapping("/{matchId}")
    public ResponseEntity<Game> getGameByMatchId(@PathVariable Integer matchId, Model model) {
        model.addAttribute("pageTitle", "경기 상세 정보");
        Optional<Game> game = matchService.getGameByMatchId(matchId);

        if(game.isPresent()) {
            return ResponseEntity.ok(game.get());
        } else {
            return ResponseEntity.notFound().build();
        }

    }
}
