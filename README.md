

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
/user/{nickname}|GET|nickname 을 통한 사용자 정보 조회 (한명, 전부 일치)|ex) nickname = 아이유
/user/{nickname}/check|GET|nickname 중복 여부 판단 (true - 사용가능한 닉네임)|ex) nickname = 아이유
/user/like/{nickname}|GET|nickname 값을 갖고 있는 모든 사용자의 정보를 조회|ex) nickname = 이
/user/all|GET|모든 사용자 정보 조회|
/user|POST|사용자 정보 등록|"username":"필수", "password":"필수", "nickname":"필수", "birthday":"필수", "gender":"MALE" or "FEMALE"
/user/id/check|POST|username 중복 여부 판단 (true - 사용가능한 닉네임)|"username":"필수"
/user/login|POST|사용자 로그인 (JWT)|"username":"필수", "password":"필수"
/user/detail/{oldNickname}|PUT|사용자 세부 정보 등록|oldNickname <- 이전 닉네임,"nickname":"필수", "profileImgURL": " ", "introduce": " "
  

## Music API 명세서
URL|METHOD|Description|Params
:---:|:---:|:---:|:---:|
/RMusics/keyword/{keyword}|GET|제목과 가수중 'keyword'가 들어간 곡들 모두 정보 조회|ex) keyword = Kanye
/RMusics/title/{title}|GET|title을 제목으로 포함한 모든 곡 정보 조회|ex) title = Jail
/RMusics/title/{title}/one|GET|곡 정보 조회|ex) title = Jail
/RMusics/all|GET|모든 곡 정보 조회|
/RMusics|POST|곡 추가|"title": "필수", "singer": "필수", "profileImgURL":" ", "lyrics":" "

  
## Team API 명세서
URL|METHOD|Description|Params
:---:|:---:|:---:|:---:|
/team/{teamName}|GET|teamName 으로 팀 조회|ex) teamName = 1번 팀
/team/all|GET|모든 팀 조회|
/team/onAir|POST|onAir 필드를 True or False로 변경|teamName:'필수'
/team|POST|팀 추가|"teamName":"1번팀", "introduce":"", "leaderName":"아이유"

## Room API 명세서
URL|METHOD|Description|Params
:---:|:---:|:---:|:---:|
/room/all|GET|생성 되어있는 방 전체 조회|
/room/{roomName}/{teamName}|GET|방 이름/팀 이름으로 방 정보 조회|ex) roomName = 1번 방, teamName = 1번팀 ※ Test data 없음
/room|POST|방 생성|"roomName":"1번방(필수)", "teamName":"1번팀(필수)", "introduce":"안녕하세요 1번 방입니다."
/room/{roomName}/{teamName}/music|POST|방에 뮤직 예약 등록|ex) roomName = 1번 방, teamName = 1번팀 / "title": "필수", "singer": "필수", "profileImgURL":" ", "lyrics":" "
/room/{roomName}/{teamName}/music|DELETE|방에 예약되어 있는 노래 삭제|ex) roomName = 1번 방, teamName = 1번팀 / "title": "필수", "singer": "필수", "profileImgURL":" ", "lyrics":" "
/room|DELETE|방 삭제|"roomName":"1번방(필수)", "teamName":"1번팀(필수)", "introduce":"안녕하세요 1번 방입니다."