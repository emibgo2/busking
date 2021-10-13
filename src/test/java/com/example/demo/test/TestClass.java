package com.example.demo.test;

import org.junit.jupiter.api.Test;

public class TestClass {
    @Test
    void htmlToReadme() {
        String s = "/user/find/{username}\tGET\tusername을 통한 사용자 정보 조회\tex) username = iu@naver.com\n" +
                "/user/{nickname}\tGET\tnickname 을 통한 사용자 정보 조회 (한명, 전부 일치)\tex) nickname = 아이유\n" +
                "/user/{nickname}/check\tGET\tnickname 중복 여부 판단 (true - 사용가능한 닉네임)\tex) nickname = 아이유\n" +
                "/user/like/{nickname}\tGET\tnickname 값을 갖고 있는 모든 사용자의 정보를 조회\tex) nickname = 이\n" +
                "/user/all\tGET\t모든 사용자 정보 조회\t\n" +
                "/user\tPOST\t사용자 정보 등록\t\"username\":\"필수\", \"password\":\"필수\", \"nickname\":\"필수\", \"birthday\":\"필수\", \"gender\":\"MALE\" or \"FEMALE\"\n" +
                "/user/id/check\tPOST\tusername 중복 여부 판단 (true - 사용가능한 닉네임)\t\"username\":\"필수\"\n" +
                "/user/login\tPOST\t사용자 로그인 (JWT)\t\"username\":\"필수\", \"password\":\"필수\"\n" +
                "/user/{oldNickname}/detail\tPUT\t사용자 세부 정보 등록\toldNickname <- 이전 닉네임,\"nickname\":\"필수\", \"profileImgURL\": \" \", \"introduce\": \" \"";


        String replace = s.replace('\t', '|');
        System.out.println("s = " + replace);
    }
}
