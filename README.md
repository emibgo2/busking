# Busking Project

## Project Name: Busking
###  Spring Frame Work
 
서버 : Heroku  
DB : My SQL  
Base URL : https://busking-back.herokuapp.com/


## User API 명세서
URL|METHOD|Description|Params
:---:|:---:|:---:|:---:|
|/user/{loginEmail}|GET|사용자 정보 조회|
|/user/all|GET|모든 사용자 정보 조회|
|/user|POST|DB에 사용자 정보를 등록합니다|loginEmail:' ', password:' ', nickName:' ', age:' ', gender:'MALE' or 'FEMALE', profileImgURL:' '


## Music API 명세서
URL|METHOD|Description|Params
:---:|:---:|:---:|:---:|
|/music/keyword/{keyword}|GET|	제목과 가수중 'keyword'가 들어간 곡들 모두 정보 조회|	ex) keyword = Kanye
|/music/title/{title}|GET|	title을 제목으로 포함한 모든 곡 정보 조회|	ex) title = Jail
|/music/title/{title}/one|GET|	곡 정보 조회|	ex) title = Jail
|/music/all|GET|	모든 곡 정보 조회	
|/music|POST|	곡 추가|title: ' ', singer: ' '
