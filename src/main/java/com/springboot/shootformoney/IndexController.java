package com.springboot.shootformoney;

import com.springboot.shootformoney.member.entity.Member;
import com.springboot.shootformoney.member.services.RankListService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class IndexController {

    private final RankListService rankListService;

    @GetMapping
    public String index(Model model){
        List<Member> rank = rankListService.getRankList();
        model.addAttribute("rank",rank);
        return "index";
    }
}
