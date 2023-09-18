package com.springboot.shootformoney.bet.controllers;

import com.springboot.shootformoney.bet.service.EuroPoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class EuroPoolController {

    private final EuroPoolService euroPoolService;
    @Autowired
    public EuroPoolController(EuroPoolService euroPoolService) {
        this.euroPoolService = euroPoolService;
    }

    //경기 정보를 사전에 EuroPool 엔티티에 저장하는 메서드.
    //자동으로 해줬으면 좋겠다.
    @PostMapping("/initialize")
    public ResponseEntity<Void> initializeEuroPools() {
        euroPoolService.addEuroPools();
        return ResponseEntity.ok().build();
    }
}
