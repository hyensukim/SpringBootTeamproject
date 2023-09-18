package com.springboot.shootformoney.member.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
public class DailyRewardController {

    @GetMapping("/reward")
    public String dailyReward(){
        return null;
    }
}