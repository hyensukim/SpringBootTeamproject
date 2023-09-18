package com.springboot.shootformoney.game.controllers;

import com.springboot.shootformoney.game.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

/*
* DB에 경기를 저장하는 PostMapping을 담당하는 Controller.
* Author: Hyedokal(https://www.github.com/Hyedokal)
*/
@Controller
public class DBController {
    private final MatchService matchService;

    @Autowired
    public DBController(MatchService matchService){this.matchService = matchService;}

}
