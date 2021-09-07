# Busking Project

## Project Name: Busking
###  Spring Frame Work
 
서버 : Heroku  
DB : My SQL  
Base URL : https://busking-back.herokuapp.com/


## User API 명세서
URL|METHOD|Description|Params
:---:|:---:|:---:|:---:|
|/user/{loginID}|GET|사용자 정보 조회|
|/user/all|GET|모든 사용자 정보 조회|
|/user|POST|DB에 사용자 정보를 등록합니다|loginID : ' ', password : ' '
