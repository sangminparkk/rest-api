# 프로젝트로 배우는 스프링
reference : [스프링 기반 REST API 개발, 백기선](https://www.inflearn.com/course/lecture?courseSlug=spring_rest-api)
* 하게 된 이유 : 기존 JPA 개발 강의를 듣다가 막혀서 우회 접근하기 위함입니다. spring security 개념 이해 + 부족한 부분 디테일 잡기 
* 프로젝트 시작일 : 10/28, 12:48PM
* 프로젝트 종료일 : 1 week

## 개발환경
* gradle
* java 17
* spring boot 3.3.5
* library
    * spring web
    * spring-boot-devtools
    * h2 database
    * lombok
    * thymeleaf
    * hateoas
    * JPA
    * postgresql (고려중)
    * restdocs
    * spring security (개발 중에 추가할 예정)

## Git Branch 전략
* main(=develop)
    * feature1, feature2, ,,,

## Things to think about
* spring security 적용을 어떻게 해야하는지
* REST API
* TDD

## Things to do
부족하다고 생각되는 개념이나 잘못 알고 있었던 모든 것들을 빠짐없이 기재하기 바랍니다. 그리고 채워넣길 바랍니다.
* spring security (1차 목표)

## Things I am doing
일주일 내로 끝냅니다. 불가능은 없다.

| 날짜    | 업무      | 비고                                    |
|-------|---------|---------------------------------------|
| 10/28 | 프로젝트 시작 | 어떻게 해서 REST API를 개발할 것인가? (Feat. HAL) |
|  |         |                                       |

## REST API
* API : Application Programming Interface
* REST : REpresentational State Transfer, **시스템 각각의 독립적인 진화를 보장**하기 위한 방법
* REST API : REST 아키텍처 스타일을 따르는 API (ex. developer-github)

* 아키텍쳐 스타일
  * Uniform Interface
    * Self-descriptive message : 메세지가 변해도 클라이언트가 메세지를 보고 해석이 가능해야 합니다. 확장 가능한 커뮤니케이션인가?!
    * HATEOAS(Hypermisa As The Engin Of Application State) : 현재 상태 기준, 하이퍼미디어(링크)를 통해 상태 변화가 가능해야 합니다. 링크 정보를 동적으로 바꿀 수 있는가?!

* REST API 체크 포인트  
응답 예시를 통해 아래 두가지를 만족하는지부터 빠르게 체크합니다.
  * 메세지만 보고도 응답을 해석할 수 있는지?
  * 해석에 대한 정보(문서 등)를 링크로 제공했는가?

* 구현 : [HAL(Hypertext Application Language)](https://stateless.co/hal_specification.html) 스펙 참고