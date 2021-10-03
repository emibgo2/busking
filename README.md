# Busking Project

## Project Name: Busking
###  Spring Frame Work
 
서버 : Heroku  
DB : My SQL  
Base URL : https://busking-back.herokuapp.com/  
로그인 방식: [JWT 토큰](https://webfirewood.tistory.com/115) 

## User API 명세서
URL|METHOD|Description|Params
:---:|:---:|:---:|:---:|
/user/find/{username}|GET|username을 통한 사용자 정보 조회|ex) username = iu@naver.com
/user/{nickname}|GET|nickname을 통한 사용자 정보 조회|ex) nickname = 이
/user/all|GET|모든 사용자 정보 조회|
/user|POST|사용자 정보 등록|"username":"필수", "password":"필수", "nickname":"필수", "birthday":"필수", "gender":"MALE" or "FEMALE"
/user/login|POST|사용자 로그인 (JWT)|"username":"필수", "password":"필수"
/user/detail|PUT|사용자 세부 정보 등록|"nickname":"필수", "profileImgURL": " ", "introduce": " "

## Music API 명세서
URL|METHOD|Description|Params
:---:|:---:|:---:|:---:|
/music/keyword/{keyword}|GET|제목과 가수중 'keyword'가 들어간 곡들 모두 정보 조회|ex) keyword = Kanye
/music/title/{title}|GET|title을 제목으로 포함한 모든 곡 정보 조회|ex) title = Jail
/music/title/{title}/one|GET|곡 정보 조회|ex) title = Jail
/music/all|GET|모든 곡 정보 조회|
/music|POST|곡 추가|"title": "필수", "singer": "필수", "profileImgURL":" ", "lyrics":" "

  
## Team API 명세서
URL|METHOD|Description|Params
:---:|:---:|:---:|:---:|
/team/{teamName}|GET|teamName 으로 팀 조회|ex) teamName = 1번 팀
/team/all|GET|모든 팀 조회|
/team/onAir|POST|onAir 필드를 True or False로 변경|teamName:'필수'
/team|POST|팀 추가|"teamName":"1번팀", "introduce":"", "leaderName":"아이유"