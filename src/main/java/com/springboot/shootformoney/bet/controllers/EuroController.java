package com.springboot.shootformoney.bet.controllers;

import com.springboot.shootformoney.member.repository.EuroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class EuroController {
    private final EuroRepository euroRepository;

    @Autowired
    public EuroController(EuroRepository euroRepository){
        this.euroRepository = euroRepository;
    }
}
