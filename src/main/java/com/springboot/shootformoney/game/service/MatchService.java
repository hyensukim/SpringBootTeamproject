package com.springboot.shootformoney.game.service;

import com.springboot.shootformoney.game.dto.MatchDto;
import com.springboot.shootformoney.game.dto.data.Match;
import com.springboot.shootformoney.game.entity.Game;
import com.springboot.shootformoney.game.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/*
* 경기 관련 데이터 조회 구현 클래스
* Author : Hyedokal (https://www.github.com/Hyedokal)
*/

@Service
public class MatchService {

    @Value("${football.api.base-url}")
    private String baseUrl;

    @Value(("${football.api.key}"))
    private String apiKey;

    @Autowired
    private GameRepository gameRepository;

    public LocalDateTime date = LocalDateTime.now();

    //기간 내의 모든 EPL 경기를 조회하는 메서드.
    public MatchDto getPLMatches() {
        LocalDate dateFrom = LocalDate.now();
        //접속일 기준 일주일 후까지의 데이터 조회.
        LocalDate dateTo = dateFrom.plusDays(7);

        URI uri = UriComponentsBuilder
                .fromUriString(baseUrl)
                .path("/competitions/2021/matches")
                .queryParam("dateFrom", dateFrom)
                .queryParam("dateTo", dateTo)
                .encode()
                .build()
                .toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Auth-Token", apiKey);
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<MatchDto> responseEntity =
                restTemplate.exchange(uri, HttpMethod.GET, entity, MatchDto.class);

        return responseEntity.getBody();
    }

    //기간 내의 모든 라리가 경기를 조회하는 메서드.
    public MatchDto getPDMatches() {
        LocalDate dateFrom = LocalDate.now();
        //접속일 기준 일주일 후까지의 데이터 조회.
        LocalDate dateTo = dateFrom.plusDays(7);

        URI uri = UriComponentsBuilder
                .fromUriString(baseUrl)
                .path("/competitions/2014/matches")
                .queryParam("dateFrom", dateFrom)
                .queryParam("dateTo", dateTo)
                .encode()
                .build()
                .toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Auth-Token", apiKey);
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<MatchDto> responseEntity =
                restTemplate.exchange(uri, HttpMethod.GET, entity, MatchDto.class);

        return responseEntity.getBody();
    }

    //기간 내의 모든 분데스리가 경기를 조회하는 메서드.
    public MatchDto getBL1Matches() {
        LocalDate dateFrom = LocalDate.now();
        //접속일 기준 일주일 후까지의 데이터 조회.
        LocalDate dateTo = dateFrom.plusDays(7);

        URI uri = UriComponentsBuilder
                .fromUriString(baseUrl)
                .path("/competitions/2002/matches")
                .queryParam("dateFrom", dateFrom)
                .queryParam("dateTo", dateTo)
                .encode()
                .build()
                .toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Auth-Token", apiKey);
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<MatchDto> responseEntity =
                restTemplate.exchange(uri, HttpMethod.GET, entity, MatchDto.class);

        return responseEntity.getBody();
    }

    //기간 내의 모든 EPL/라리가/분데스리가 경기 조회 메서드.
    public List<MatchDto> getAllMatches() {
        List<MatchDto> allMatches = new ArrayList<>();

        // EPL 경기 정보 가져오기
        MatchDto eplMatches = getPLMatches();
        allMatches.add(eplMatches);

        // 라리가 경기 정보 가져오기
        MatchDto pdMatches = getPDMatches();
        allMatches.add(pdMatches);

        // 분데스리가 경기 정보 가져오기
        MatchDto bl1matches= getBL1Matches();
        allMatches.add(bl1matches);

        return allMatches;
    }

    //외부 API에서 PL/라리가/분데스 경기 정보를 가져오고 DB에 저장하는 메서드.
    public void saveAllMatchesToDB(){
        MatchDto plMatches = getPLMatches();
        MatchDto pdMatches = getPDMatches();
        MatchDto bl1Matches = getBL1Matches();
        List<Game> games = convertToEntity(plMatches.getMatches());
        games.addAll(convertToEntity(pdMatches.getMatches()));
        games.addAll(convertToEntity(bl1Matches.getMatches()));
        gameRepository.saveAll(games);
    }

    //외부 API에서 JSON 형식으로 받아 온 데이터를 DB에 저장할 수 있는 List<Game>으로 변환하는 메서드.
    private List<Game> convertToEntity(List<Match> matches){
        return matches.stream()
                .map(match -> Game.builder()
                        .gLeague(match.getCompetition().getName())
                        .gHomeTeam(match.getHomeTeam().getName())
                        .gAwayTeam(match.getAwayTeam().getName())
                        .gStartTime(match.getUtcDate())
                        .gHomeScore(match.getScore().getFullTime().getHome())
                        .gAwayScore(match.getScore().getFullTime().getAway())
                        .build())
                .collect(Collectors.toList());
    }
}
