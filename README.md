# Shoot For Money

## 1. 프로젝트 소개

### 🎇 개요

- 프로젝트명 : 슛 포 머니
- 개발 기간 : 2023.09.04 ~ 2023.09.26
- 개발 인원 : 6명
- 핵심 기능 : 해외 축구 결과 예측 및 모의 배팅 웹 애플리케이션

### 🎇 기획 배경 및 기대 효과

- 주요 타겟층 : 20대 남성
- 기획 배경
    - 주 타겟층의 관심사 중 '해외 축구 리그'에 초점을 맞추어 경기 결과를 예측하고 배팅한 결과에 따라 배당율에 따른 포인트를 얻도록 설계하면, 저희 팀만의 개성있는 프로젝트가 완성될 것으로 기대되어 기획함.

## 2. 개발 환경

- Intelli J
- Spring Boot framework
- Spring-Security
- Gradle
- Java
- html, css, js, bootStrap
- ThymeLeaf
- Spring Data JPA
- GitHub
- MySQL
- Apache Tomcat

# 3. UserFlow 작성

- Client 가 웹 사이트 방문 시 flow chart 작성

![userflow](https://raw.githubusercontent.com/hyensukim/SpringBootTeamproject/main/image/UserFlow.jpg)


# 4. 기능 명세서

## 🎇 관리자 페이지

### ✅ 기본 설정

- 사이트 title 수정.
    - Ex) 사이트명 | 페이지명 -> 사이트명을 관리자페이지에서 입력을 하면, 전체 페이지에서 저 이름이 바뀌도록 구현하겠다.
- 사이트 설명 수정.
- css & js 버전 수정.
- 회원 규제 룰.

### ✅ 회원 관리

- 권한 변경(Member, Admin)
- 회원 전체 조회
- 회원 탈퇴

### ✅ 게시판 관리

- 페이지 관련 기능
  - 한 페이지에 들어갈 갯수
  - 하부 페이지 번호 갯수
- 한 목록에 들어갈 게시글 갯수
- 파일 업로드/다운로드 가능 여부

### ✅ 게시글 관리

- 게시글 작성 폼(파일 업로드 O/X)
- 게시글 삭제


## 🎇 회원

### ✅ 공통

- 권한

    - ADMIN / MEMBER 인가. 
    - 가입 시 일반 회원(Member)

- 등급

    - 회원별 차등 수수료 할당을 위한 등급
    - 컨퍼런스(하)/유로파(중)/챔피언스(상)
    - 등급 산정 기준 : 총 배팅 금액(누적액)

- 포인트(단위 _유로)

    - 회원가입 시 100만 유로 지급.
    - 추가 포인트 획득
        1. 출석 포인트(1일 1회, 20만 유로)
        2. 광고 시청 포인트(제한 X, 2만 유로)
    - 포인트 상한 5000만 유로 이상 시 졸업.
        - 졸업 보상 : Lv1 씩 증가하며, 레벨마다 회원 닉네임 폰트 뒤에 백그라운드 색깔 변경.
        - Lv10 단계까지 (백그라운드 색X,무지개(남 제외),은금검)

### ✅ 로그인

- 비밀번호는 암호화(hashing) 과정을 거쳐 DB에 저장.

### ✅ 회원가입

- ID : 영문과 숫자 조합으로 4자리 이상 16자리 이하 제한, 중복 여부 확인.
- PW : 영문, 숫자, 특문 조합으로 8자리이상 16자리 이하 제한
- 이름 : 한글 입력, 2자리 이상 30자리 이하, 공백 입력 제한
- 닉네임 : 영문,숫자 조합으로 4자리 이상 16자리 이하 제한, 중복 여부 확인.
- E-mail : 이메일 양식으로 입력 제한.
- 모바일 : 회원 중복 가입 방지용, 입력 값 유효성 검증 시 정규표현식(Regex) 사용한 검증.

### ✅ ID 찾기

- 이름, 이메일 입력 받아서 조회.
- 맞으면 아이디 정보 메시지 출력 후 확인 시 로그인페이지로 넘어감.
- 틀리면 없는 정보라고 메시지 뜸.

### ✅ 비밀번호 찾기

- 아이디, 이메일 입력 받아서 조회.
- 맞으면 이메일로 '임시 비밀번호' 및 로그인 링크 보냄.
- 틀리면 없는 정보라고 메시지 뜸.

### ✅ 마이페이지

- 회원 정보 조회 및 수정, 회원 탈퇴 - 마이페이지 이동 전 비밀번호로 확인.
- 배팅 내역 조회 (배팅 금액, 배팅 시간, 배당율, 경기 정보)
- 본인이 작성한 게시글 조회(작성일, 작성자, 제목, 조회수)

## 🎇 게시글

### ✅ 게시글 검색

- 제목, 작성자(닉네임), 내용으로 조회.

### ✅ 파일 업로드 & 다운로드

- 이미지 파일 업로드 가능하도록 구현.(multipart/form-data)

## 🎇 승부 예측 및 배팅

### ✅ 배팅하기

- 금액 입력 : '금액 입력값' 만 포인트(입력값에 X 10,000)
- All in(올인) 기능

    - 보유한 포인트 전체 배팅
    - 만의 자리로 제한한 값만 배팅 가능 하도록 함.
    - Ex) 180,500 -> 180,000
- 배팅 1회 시 승/무/패 중 하나만 선택 가능.
- 중복 배팅 가능.

    - Ex) 한 계정 -> '승' 10만 배팅 후, '무' 10만 배팅 가능.
- 배팅 성공

    - 배당금액 : 회원 배팅 금액 * 배당율.
    - 배당율 : 전회원 배팅 총금액(3) / 적중 총금액(1), 소수 둘째 자릿까지 반올림.
    - 지급금 : 배당금액 * 1- 회원 등급에 따른 수수료 비율.
- 지급금 지급

    - 자동으로 추가되도록 구현
    - 회원은 마이페이지 배팅 결과에서 확인.
- 배팅 가능 시간 제한

    - 경기 시작 전까지만 배팅 가능하도록 구현.

### ✅ 배팅취소

- 배팅 취소 가능 시간 제한
    - 경기 시작 전까지만 취소 가능하도록 구현.
- 취소 확인 메시지, 취소한 금액 환급, 배당율에 적용되도록 로직 구현.

## 🎇 댓글

- 내용 글자수 1자 이상 100자 이하 제한.

# 5. ERD 작성

[ERD 관계](https://raw.githubusercontent.com/hyensukim/Ait/main/%EC%82%AC%EC%A7%84/%EC%BB%A4%EB%AE%A4%EB%8B%88%ED%8B%B0%20(8).png "Ver1")

# 6. 테이블 다이어그램

## Member

| 키 | 논리     | 물리        | 엔티티                                   | 타입                                     | UNIQUE | Null 허용 | 기본값     | 코멘트                 |
| -- | -------- | ----------- | ---------------------------------------- | ---------------------------------------- | ------ | --------- | ---------- | ---------------------- |
| PK | 회원번호 | m_no        | Integer                                  | int                                      |        | N         |            | AUTO_INCREMENT         |
|    | 아이디   | m_id        | String                                   | varchar                                  | Y      | N         |            | 4자리 이상 16자리 이하 |
|    | 비밀번호 | m_password  | String                                   | varchar                                  |        | N         |            | 8자리이상 16자리 이하  |
|    | 이름     | m_name      | String                                   | varchar                                  |        | N         |            | 2자리 이상 30자리 이하 |
|    | 휴대번호 | m_phone     | String                                   | varchar                                  | Y      | N         |            | 11자리                 |
|    | 이메일   | m_email     | String                                   | varchar                                  | Y      | N         |            | 30자 이하              |
|    | 생성일자 | m_createdAt | LocalDateTime                            | timestamp                                |        |           |            |                        |
|    | 수정일자 | m_updatedAt | LocalDateTime                            | timestamp                                |        |           |            |                        |
|    | 별명     | m_nickName  | String                                   | varchar                                  | Y      | N         |            | 4자리 이상 16자리 이하 |
|    | 등급     | m_grade     | Enum('CONFERECE', 'EUROPA', 'CHAMPIONS') | Enum('CONFERECE', 'EUROPA', 'CHAMPIONS') |        | N         | CONFERENCE |                        |
|    | 권한     | m_role      | Enum('MEMBER', 'ADMIN')                  | Enum('MEMBER', 'ADMIN')                  |        | N         | MEBER      |                        |
|    | 레벨     | m_level     | Integer                                  | tinyInt                                  |        | N         | Lv1        |                        |

## Post

| 키 | 논리        | 물리        | 엔티티        | 타입      | UNIQUE | Null 허용 | 기본값 | 코멘트         |
| -- | ----------- | ----------- | ------------- | --------- | ------ | --------- | ------ | -------------- |
| PK | 게시글 번호 | p_no        | Integer       | int       |        | N         |        | AUTO_INCREMENT |
| FK | 회원번호    | m_no        | Integer       | int       |        | N         |        |                |
| FK | 게시판 번호 | b_no        | Integer       | int       |        | N         |        |                |
|    | 제목        | p_title     | String        | varchar   |        | N         |        | 100자 이하     |
|    | 내용        | p_content   | String        | longtext  |        | N         |        | 3000자 이하    |
|    | 생성일자    | p_createdAt | LocalDateTime | timestamp |        |           |        |                |
|    | 수정일자    | p_updatedAt | LocalDateTime | timestamp |        |           |        |                |
|    | 조회수      | view        | Integer       | int       |        | N         | 0      |                |

## Board

| 키 | 논리                 | 물리     | 엔티티  | 타입    | UNIQUE | Null 허용 | 기본값 | 코멘트     |
| -- | -------------------- | -------- | ------- | ------- | ------ | --------- | ------ | ---------- |
| PK | 게시판 번호          | b_no     | Integer | int     |        | N         |        |            |
|    | 파일 작성 여부       | b_isFile | Boolean | tinyInt |        | N         | FALSE  |            |
|    | 게시판명             | b_name   | String  | varchar |        | N         |        | 100자 이하 |
|    | 페이지 수            | b_pageNo | Integer | int     |        | N         | 10     |            |
|    | 리스트당 게시글 갯수 | b_unitNo | Integer | int     |        | N         | 15     |            |

## Comment

| 키 | 논리        | 물리        | 엔티티        | 타입      | UNIQUE | Null 허용 | 기본값 | 코멘트      |
| -- | ----------- | ----------- | ------------- | --------- | ------ | --------- | ------ | ----------- |
| PK | 댓글 번호   | c_no        | Integer       | int       |        | N         |        |             |
| FK | 회원번호    | m_no        | Integer       | int       |        | N         |        |             |
| FK | 게시글 번호 | p_no        | Integer       | int       |        | N         |        |             |
|    | 내용        | c_content   | String        | longtext  |        | N         |        | 1000자 이하 |
|    | 작성일      | c_createdAt | LocalDateTime | timestamp |        |           |        |             |
|    | 수정일      | c_updatedAt | LocalDateTime | timestamp |        |           |        |             |

## Euro

| 키 | 논리     | 물리    | 엔티티  | 타입 | UNIQUE | Null 허용 | 기본값    | 코멘트 |
| -- | -------- | ------- | ------- | ---- | ------ | --------- | --------- | ------ |
| FK | 회원번호 | m_no    | Integer | int  |        | N         |           |        |
|    | 값       | E_value | Integer | int  |        | N         | 1,000,000 |        |

## Game

| 키 | 논리          | 물리         | 엔티티                      | 타입                        | UNIQUE | Null 허용 | 기본값 | 코멘트     |
| -- | ------------- | ------------ | --------------------------- | --------------------------- | ------ | --------- | ------ | ---------- |
| PK | 경기 번호     | g_no         | Integer                     | int                         |        | N         |        |            |
|    | 홈 팀         | g_home_team  | String                      | varchar                     |        | N         |        | 100자 이하 |
|    | 어웨이 팀     | g_away_team  | String                      | varchar                     |        | N         |        | 100자 이하 |
|    | 경기 시작시간 | g_start_time | LocalDateTime               | timestamp                   |        | N         |        |            |
|    | 경기 종료시간 | g_end_time   | LocalDateTime               | timestamp                   |        | N         |        |            |
|    | 경기결과      | g_result     | Enum('WIN', 'DRAW', 'LOSE') | Enum('WIN', 'DRAW', 'LOSE') |        | N         |        | 홈팀기준   |
|    | 홈 점수       | g_home_score | Integer                     | tinyInt                     |        | N         | 0      |            |
|    | 어웨이 점수   | g_away_score | Integer                     | tinyInt                     |        | N         | 0      |            |

## Chatting

| 키 | 논리      | 물리         | 엔티티        | 타입      | UNIQUE | Null 허용 | 기본값 | 코멘트     |
| -- | --------- | ------------ | ------------- | --------- | ------ | --------- | ------ | ---------- |
| FK | 회원번호  | m_no         | Integer       | int       |        | N         |        |            |
| FK | 경기 번호 | g_no         | Integer       | int       |        | N         |        |            |
|    | 내용      | ch_content   | String        | varchar   |        | N         |        | 100자 이하 |
|    | 작성시간  | ch_createdAt | LocalDateTime | timestamp |        |           |        |            |

## Batting

| 키 | 논리      | 물리      | 엔티티        | 타입      | UNIQUE | Null 허용 | 기본값 | 코멘트               |
| -- | --------- | --------- | ------------- | --------- | ------ | --------- | ------ | -------------------- |
| PK | 배팅 번호 | bt_no     | Integer       | int       |        | N         |        |                      |
| FK | 경기 번호 | g_no      | Integer       | int       |        | N         |        |                      |
| FK | 회원번호  | m_no      | Integer       | int       |        | N         |        |                      |
|    | 배팅금액  | bt_money  | Integer       | int       |        | N         | 1      | 최소 금액 1 * 10,000 |
|    | 배팅 결과 | bt_result | String        | varchar   |        | N         |        |                      |
|    | 배팅 시간 | bt_time   | LocalDateTime | timestamp |        |           |        |                      |
|    | 배당율    | bt_ratio  | Float         | float     |        | N         |        | game 종료 후 배당률  |

## Ratio

| 키 | 논리      | 물리       | 엔티티  | 타입  | UNIQUE | Null 허용 | 기본값 | 코멘트 |
| -- | --------- | ---------- | ------- | ----- | ------ | --------- | ------ | ------ |
| FK | 경기 번호 | g_no       | Integer | int   |        | N         |        |        |
|    | 승 배당율 | win_ratio  | Float   | float |        | N         | 0.00   |        |
|    | 무 배당율 | draw_ratio | Float   | float |        | N         | 0.00   |        |
|    | 패 대방율 | lose_ratio | Float   | float |        | N         | 0.00   |        |

# 7. 역할 분담

- 회원 : 김현수
- 관리자 : 권윤환
- 게시글 : 이창진
- 댓글 + 페이징 : 박병호
- 승부 예측 및 배팅 : 정호진
- 프론트 : 신예성

# 8. 일정표

![1694355767033](https://raw.githubusercontent.com/hyensukim/SpringBootTeamproject/main/image/%EC%9D%BC%EC%A0%95%ED%91%9C.png)
