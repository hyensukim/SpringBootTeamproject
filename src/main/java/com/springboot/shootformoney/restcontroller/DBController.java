package com.springboot.shootformoney.restcontroller;

import com.springboot.shootformoney.game.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
