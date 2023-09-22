package com.springboot.shootformoney.game.controllers;

import com.springboot.shootformoney.bet.service.EuroPoolService;
import com.springboot.shootformoney.bet.service.EuroService;
import com.springboot.shootformoney.game.entity.Game;
import com.springboot.shootformoney.game.service.MatchService;
import com.springboot.shootformoney.member.utils.MemberUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/*
* 경기 정보를 Get하는 Controller.
* Author: Hyedokal(https://www.github.com/Hyedokal)
*/
@Controller
@RequestMapping("/list")
@RequiredArgsConstructor
public class MatchController {

    private final MatchService matchService;
    private final EuroPoolService euroPoolService;
    private final EuroService euroService;
    private final MemberUtil memberUtil;


    @GetMapping("/unstarted/epl")
    public String unstartedPLMatches(Model model){
        model.addAttribute("pageTitle", "EPL 경기 목록");
        Map<Integer, List<Double>> ratios = new HashMap<>();
        List<Game> games = matchService.getUnstartedPLMatches();
        for(Game game : games){
            ratios.put(game.getMatchId(), euroPoolService.calculateRatio(game.getMatchId()));
        }
        model.addAttribute("allGames", games);
        model.addAttribute("ratios", ratios);
        return "prediction-EPL";
    }

    @GetMapping("/unstarted/laliga")
    public String unstartedPDMatches(Model model){
        model.addAttribute("pageTitle", "라리가 경기 목록");
        Map<Integer, List<Double>> ratios = new HashMap<>();
        List<Game> games = matchService.getUnstartedPDMatches();
        for(Game game : games){
            ratios.put(game.getMatchId(), euroPoolService.calculateRatio(game.getMatchId()));
        }
        model.addAttribute("allGames", games);
        model.addAttribute("ratios", ratios);
        return "prediction-LaLiga";
    }
    @GetMapping("/unstarted/bundes")
    public String unstartedBL1Matches(Model model){
        model.addAttribute("pageTitle", "분데스 경기 목록");
        Map<Integer, List<Double>> ratios = new HashMap<>();
        List<Game> games = matchService.getUnstartedBL1Matches();
        for(Game game : games){
            ratios.put(game.getMatchId(), euroPoolService.calculateRatio(game.getMatchId()));
        }
        model.addAttribute("allGames", games);
        model.addAttribute("ratios", ratios);
        return "prediction-Bundesliga";
    }
    @GetMapping("/unstarted/entirelist")
    public String everyUnstartedMatches(Model model){
        model.addAttribute("pageTitle", "전체 경기 목록");
        Map<Integer, List<Double>> ratios = new HashMap<>();
        List<Game> games = matchService.getAllUnstartedMatches();
        for(Game game : games){
            ratios.put(game.getMatchId(), euroPoolService.calculateRatio(game.getMatchId()));
        }
        model.addAttribute("allGames", games);
        model.addAttribute("ratios", ratios);
        return "prediction";
    }

    @GetMapping("/finished/epl")
    public String finishedPLMatches(Model model){
        model.addAttribute("pageTitle", "종료된 EPL 경기 목록");
        Map<Integer, List<Double>> ratios = new HashMap<>();
        List<Game> games = matchService.getFinishedPLMatches();
        for(Game game : games){
            ratios.put(game.getMatchId(), euroPoolService.calculateRatio(game.getMatchId()));
        }
        model.addAttribute("allGames", games);
        model.addAttribute("ratios", ratios);
        return "finished-EPL";
    }

    @GetMapping("/finished/laliga")
    public String finishedPDMatches(Model model){
        model.addAttribute("pageTitle", "종료된 라리가 경기 목록");
        Map<Integer, List<Double>> ratios = new HashMap<>();
        List<Game> games = matchService.getFinishedPDMatches();
        for(Game game : games){
            ratios.put(game.getMatchId(), euroPoolService.calculateRatio(game.getMatchId()));
        }
        model.addAttribute("allGames", games);
        model.addAttribute("ratios", ratios);
        return "finished-Laliga";
    }

    @GetMapping("/finished/bundes")
    public String finishedBL1Matches(Model model){
        model.addAttribute("pageTitle", "종료된 분데스 경기 목록");
        Map<Integer, List<Double>> ratios = new HashMap<>();
        List<Game> games = matchService.getFinishedBL1Matches();
        for(Game game : games){
            ratios.put(game.getMatchId(), euroPoolService.calculateRatio(game.getMatchId()));
        }
        model.addAttribute("allGames", games);
        model.addAttribute("ratios", ratios);
        return "finished-Bundesliga";
    }

    @GetMapping("/finished/entirelist")
    public String everyFinishedMatches(Model model){
        model.addAttribute("pageTitle", "종료된 경기 목록");
        Map<Integer, List<Double>> ratios = new HashMap<>();
        List<Game> games = matchService.getAllFinishedMatches();
        for(Game game : games){
            ratios.put(game.getMatchId(), euroPoolService.calculateRatio(game.getMatchId()));
        }
        model.addAttribute("allGames", games);
        model.addAttribute("ratios", ratios);
        return "finished";
    }

    @GetMapping("/{matchId}")
    public String getGameByMatchId(@PathVariable Integer matchId, Model model) {
        model.addAttribute("pageTitle", "경기 상세 정보");
        model.addAttribute("matchId", matchId);
        Game game = matchService.getGameInfo(matchId)
                .orElseThrow(() -> new RuntimeException("잘못된 경기 정보 요청입니다."));
        model.addAttribute("game", game);
        List<Double> ratios = euroPoolService.calculateRatio(matchId);
        model.addAttribute("ratios", ratios);
        Integer totalEuro = euroService.getTotalEuro();
        model.addAttribute("totalEuro", totalEuro);
        return "game-info";
    }
}
