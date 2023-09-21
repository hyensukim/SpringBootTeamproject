package com.springboot.shootformoney;

import com.springboot.shootformoney.bet.service.EuroPoolService;
import com.springboot.shootformoney.game.entity.Game;
import com.springboot.shootformoney.game.service.MatchService;
import com.springboot.shootformoney.member.entity.Member;
import com.springboot.shootformoney.member.services.RankListService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class IndexController {

    private final RankListService rankListService;
    private final MatchService matchService;
    private final EuroPoolService euroPoolService;
    @GetMapping
    public String index(Model model){
        List<Member> rank = rankListService.getRankList();
        model.addAttribute("rank",rank);

        //배팅 쪽 컨트롤러 구현 시작
        Map<Integer, List<Double>> ratios = new HashMap<>();
        List<Game> games = matchService.getAllUnstartedMatches();
        for(Game game : games){
            ratios.put(game.getMatchId(), euroPoolService.calculateRatio(game.getMatchId()));
        }
        model.addAttribute("allGames", games);
        model.addAttribute("ratios", ratios);
        //배팅 쪽 컨트롤러 구현 끝
        return "index";
    }
}
