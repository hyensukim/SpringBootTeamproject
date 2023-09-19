package com.springboot.shootformoney.bet.controllers;

import com.springboot.shootformoney.bet.entity.EuroPool;
import com.springboot.shootformoney.bet.service.EuroPoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
public class EuroPoolController {

    private final EuroPoolService euroPoolService;
    @Autowired
    public EuroPoolController(EuroPoolService euroPoolService) {
        this.euroPoolService = euroPoolService;
    }

    //각 경기의 실시간 승무패 배당률 불러오는 메서드
}
