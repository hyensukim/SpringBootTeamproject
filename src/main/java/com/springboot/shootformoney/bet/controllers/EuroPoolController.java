package com.springboot.shootformoney.bet.controllers;

import com.springboot.shootformoney.bet.service.EuroPoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EuroPoolController {

    private final EuroPoolService euroPoolService;
    @Autowired
    public EuroPoolController(EuroPoolService euroPoolService) {
        this.euroPoolService = euroPoolService;
    }

    //경기 정보를 사전에 EuroPool 엔티티에 저장하는 메서드.
    @PostMapping("/initialize")
    public ResponseEntity<Void> initializeEuroPools() {
        euroPoolService.addEuroPools();
        return ResponseEntity.ok().build();
    }
}
