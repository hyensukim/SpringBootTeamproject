package com.springboot.shootformoney.game.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.springboot.shootformoney.game.dto.data.*;
import lombok.*;

import java.util.List;

/*
* 외부 API를 사용하기 위한 DTO클래스 작성
* Author: Hyedokal(https://www.github.com/Hyedokal)
*/
@Data
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MatchDto {
    @JsonProperty("matches")
    private List<Matches> matches;
}
