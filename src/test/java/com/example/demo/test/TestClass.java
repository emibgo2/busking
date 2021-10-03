package com.example.demo.test;

import org.junit.jupiter.api.Test;

public class TestClass {
    @Test
    void htmlToReadme() {
        String s = "/team/{teamName}\tGET\tteamName 으로 팀 조회\tex) teamName = 1번 팀\n" +
                "/team/all\tGET\t모든 팀 조회\t\n" +
                "/team/onAir\tPOST\tonAir 필드를 True or False로 변경\tteamName:'필수'\n" +
                "/team\tPOST\t팀 추가\t\"teamName\":\"1번팀\", \"introduce\":\"\", \"leaderName\":\"아이유\"";


        String replace = s.replace('\t', '|');
        System.out.println("s = " + replace);
    }
}
