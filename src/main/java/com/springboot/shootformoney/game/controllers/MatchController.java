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

import java.util.List;
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
    @ResponseBody
    public String unstartedPLMatches(Model model){
        model.addAttribute("pageTitle", "EPL 경기 목록");
        List<Game> unstartedPLMatches = matchService.getUnstartedPLMatches();
        model.addAttribute("EPLgames", unstartedPLMatches);
        return "prediction-EPL";
    }

    @GetMapping("/unstarted/laliga")
    @ResponseBody
    public String unstartedPDMatches(Model model){
        model.addAttribute("pageTitle", "라리가 경기 목록");
        List<Game> unstartedPDMatches = matchService.getUnstartedPDMatches();
        model.addAttribute("PDgames", unstartedPDMatches);
        return "prediction-LaLiga";
    }
    @GetMapping("/unstarted/bundes")
    @ResponseBody
    public String unstartedBL1Matches(Model model){
        model.addAttribute("pageTitle", "분데스 경기 목록");
        List<Game> unstartedBL1Matches = matchService.getUnstartedBL1Matches();
        model.addAttribute("Bundesgames", unstartedBL1Matches);
        return "prediction-Bundesliga";
    }
    @GetMapping("/unstarted/entirelist")
    @ResponseBody
    public String everyUnstartedMatches(Model model){
        model.addAttribute("pageTitle", "전체 경기 목록");
        List<Game> allUnstartedMatches = matchService.getAllUnstartedMatches();
        model.addAttribute("Allgames", allUnstartedMatches);
        return "prediction";
    }

    @GetMapping("/finished/epl")
    @ResponseBody
    public String finishedPLMatches(Model model){
        model.addAttribute("pageTitle", "종료된 EPL 경기 목록");
        List<Game> finishedPLMatches = matchService.getFinishedPLMatches();
        model.addAttribute("EPLgames", finishedPLMatches);
        return "finished-EPL";
    }

    @GetMapping("/finished/laliga")
    @ResponseBody
    public String finishedPDMatches(Model model){
        model.addAttribute("pageTitle", "종료된 라리가 경기 목록");
        List<Game> finishedPDMatches = matchService.getFinishedPDMatches();
        model.addAttribute("PDgames", finishedPDMatches);
        return "finished-LaLiga";
    }

    @GetMapping("/finished/bundes")
    @ResponseBody
    public String finishedBL1Matches(Model model){
        model.addAttribute("pageTitle", "종료된 분데스 경기 목록");
        List<Game> finishedBL1Matches = matchService.getFinishedBL1Matches();
        model.addAttribute("Bundesgames", finishedBL1Matches);
        return "finished-Bundesliga";
    }

    @GetMapping("/finished/entirelist")
    @ResponseBody
    public String everyFinishedMatches(Model model){
        model.addAttribute("pageTitle", "종료된 경기 목록");
        List<Game> allFinishedMatches = matchService.getAllFinishedMatches();
        model.addAttribute("Allgames", allFinishedMatches);
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
        Integer totalEuro = euroService.getTotalEuro(memberUtil.getMember().getMNo());
        model.addAttribute("totalEuro", totalEuro);
        return "game-info";
    }
}
