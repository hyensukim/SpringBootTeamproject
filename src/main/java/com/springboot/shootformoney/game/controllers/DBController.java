package com.springboot.shootformoney.game.controllers;

import com.springboot.shootformoney.game.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/*
* DB에 경기를 저장하는 PostMapping을 담당하는 Controller.
* Author: Hyedokal(https://www.github.com/Hyedokal)
*/
@RestController
public class DBController {
    private final MatchService matchService;

    @Autowired
    public DBController(MatchService matchService){this.matchService = matchService;}

    @PostMapping("/saveGames")
    public ResponseEntity<String> saveAllData(){
        try{
            matchService.saveAllMatchesToDB();
            return new ResponseEntity<>("All matches saved successfully", HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/updateGames")
    public ResponseEntity<String> updateAllEndedGames() {
        try{
            matchService.updateAllEndedGames();
            return new ResponseEntity<>("All matches updated successfully", HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
