package com.springboot.shootformoney.restcontroller;

import com.springboot.shootformoney.game.dto.MatchDto;
import com.springboot.shootformoney.game.service.MatchService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MatchController {

    private final MatchService matchService;
    public MatchController(MatchService matchService){
        this.matchService = matchService;
    }
    @GetMapping("/epl")
    @ResponseBody
    public MatchDto plMatches(){

        return matchService.getPLMatches();
    }

    @GetMapping("/laliga")
    @ResponseBody
    public MatchDto pdMatches(){

        return matchService.getPDMatches();
    }
    @GetMapping("/bundes")
    @ResponseBody
    public MatchDto bl1Matches(){

        return matchService.getBL1Matches();
    }
    @GetMapping("/entirelist")
    @ResponseBody
    public List<MatchDto> everyMatches(){

        return matchService.getAllMatches();
    }
}
