package com.example.demo.test;

import org.junit.jupiter.api.Test;

public class TestClass {
    @Test
    void htmlToReadme() {

        String te = "We couldn't turn around~'Til we were upside down~I'll be the bad guy now~But no, I ain't too proud~I couldn't be there~Even when I tried~You don't believe it~We do this every time~Seasons change and our love went cold~Feed the flame 'cause we can't let it go~Run away, but we're running in circles~Run away, run away~I dare you to do something~I'm waiting on you again~So I don't take the blame~Run away, but we're running in circles~Run away, run away, run away~Let go~I got a feeling that it's time to let go~I say so~I knew that this was doomed from the get-go~You thought that it was special, special~But it was just the sex though, the sex though~And I still hear the echoes (the echoes)~I got a feeling that it's time to let it go, let it go~Seasons change and our love went cold~Feed the flame 'cause we can't let it go~Run away, but we're running in circles~Run away, run away~I dare you to do something~I'm waiting on you again~So I don't take the blame~Run away, but we're running in circles~Run away, run away, run away~Maybe you don't understand what I'm going through~It's only me~What you got to lose?~Make up your mind, tell me~What are you gonna do?~It's only me~Let it go~Seasons change and our love went cold~Feed the flame 'cause we can't let it go~Run away, but we're running in circles~Run away, run away~I dare you to do something~I'm waiting on you again~So I don't take the blame~Run away, but we're running in circles~Run away, run away, run away~";
        String re = te.replace("~", "\n");

        System.out.println("re = \n" + re);
        String s = "/room/all\tGET\t생성 되어있는 방 전체 조회\t\n" +
                "/room/{roomName}/{teamName}\tGET\t방 이름/팀 이름으로 방 정보 조회\tex) roomName = 1번 방, teamName = 1번팀 ※ Test data 없음\n" +
                "/room\tPOST\t방 생성\t\"roomName\":\"1번방(필수)\", \"teamName\":\"1번팀(필수)\", \"introduce\":\"안녕하세요 1번 방입니다.\"\n" +
                "/room/{roomName}/{teamName}/music\tPOST\t방에 뮤직 예약 등록\tex) roomName = 1번 방, teamName = 1번팀 / \"title\": \"필수\", \"singer\": \"필수\", \"profileImgURL\":\" \", \"lyrics\":\" \"\n" +
                "/room/{roomName}/{teamName}/music\tDELETE\t방에 예약되어 있는 노래 삭제\tex) roomName = 1번 방, teamName = 1번팀 / \"title\": \"필수\", \"singer\": \"필수\", \"profileImgURL\":\" \", \"lyrics\":\" \"\n" +
                "/room\tDELETE\t방 삭제\t\"roomName\":\"1번방(필수)\", \"teamName\":\"1번팀(필수)\", \"introduce\":\"안녕하세요 1번 방입니다.\"";


        String replace = s.replace('\t', '|');
        System.out.println("s = " + replace);
    }
}
