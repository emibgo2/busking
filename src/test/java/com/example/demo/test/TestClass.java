package com.example.demo.test;

import org.junit.jupiter.api.Test;

public class TestClass {
    @Test
    void htmlToReadme() {

        String te = "We couldn't turn around~'Til we were upside down~I'll be the bad guy now~But no, I ain't too proud~I couldn't be there~Even when I tried~You don't believe it~We do this every time~Seasons change and our love went cold~Feed the flame 'cause we can't let it go~Run away, but we're running in circles~Run away, run away~I dare you to do something~I'm waiting on you again~So I don't take the blame~Run away, but we're running in circles~Run away, run away, run away~Let go~I got a feeling that it's time to let go~I say so~I knew that this was doomed from the get-go~You thought that it was special, special~But it was just the sex though, the sex though~And I still hear the echoes (the echoes)~I got a feeling that it's time to let it go, let it go~Seasons change and our love went cold~Feed the flame 'cause we can't let it go~Run away, but we're running in circles~Run away, run away~I dare you to do something~I'm waiting on you again~So I don't take the blame~Run away, but we're running in circles~Run away, run away, run away~Maybe you don't understand what I'm going through~It's only me~What you got to lose?~Make up your mind, tell me~What are you gonna do?~It's only me~Let it go~Seasons change and our love went cold~Feed the flame 'cause we can't let it go~Run away, but we're running in circles~Run away, run away~I dare you to do something~I'm waiting on you again~So I don't take the blame~Run away, but we're running in circles~Run away, run away, run away~";
        String re = te.replace("~", "\n");

        System.out.println("re = \n" + re);
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
