package com.springboot.shootformoney.restcontroller;

import com.fasterxml.jackson.databind.deser.DataFormatReaders;
import com.springboot.shootformoney.game.service.MatchService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RestControllers {

    private final MatchService matchService;
    public RestControllers(MatchService matchService){
        this.matchService = matchService;
    }
    @GetMapping("/testing")
    public String getResult(){

        return matchService.getAllEPLMatch();
    }

}
