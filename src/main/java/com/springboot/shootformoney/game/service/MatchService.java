package com.springboot.shootformoney.game.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;

/*
* 경기 관련 데이터 조회 구현 클래스
* Author : Hyedokal (https://www.github.com/Hyedokal)
*/

@Service
public class MatchService {

    public LocalDateTime date = LocalDateTime.now();

    //기간 내의 모든 EPL 경기를 조회하는 메서드.
    public String getAllEPLMatch() {
        URI uri = UriComponentsBuilder
                .fromUriString("https://api.football-data.org/v4")
                .path("/competitions/2021/matches?matchday=4")
                .encode()
                .build()
                .toUri();

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(uri, String.class);

        return responseEntity.getBody();
    }
}
