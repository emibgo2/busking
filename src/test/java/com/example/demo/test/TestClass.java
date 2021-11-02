package com.example.demo.test;

import org.junit.jupiter.api.Test;

public class TestClass {
    @Test
    void htmlToReadme() {


        String s = "/team/{teamName}\tPUT\t팀 정보 변경\tex) teamName = 1번팀 / \"teamName\":\"1번팀\", \"introduce\":\"\", \"leaderName\":\"아이유\"";

        String replace = s.replace('\t', '|');
        System.out.println("s = " + replace);
    }
}
