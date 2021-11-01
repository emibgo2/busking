package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class HomeController {
    @GetMapping({"/"})
    public String index(Model model) {
        /**
         * 항상 작성 전에 필독!
         * 사용법
         * 1. List<Method> 모델이름Controller 으로 만들것
         * 2. 각자 컨트롤러에 해당하는 Method( URL 정보 ) 를 삽입
         ★★★★!!!중요!!!★★★★
         add() 되는 곳이 어디인지 확인하기
         * 3. 이후 methodList에 put("모델이름", 모델이름Controller ) 넣기!
         * ★★ put에 모델이름 잘들어갔는지 확인 ★★
         * 끝
         */

        // UserController 부분 API URL
        List<Method> userController = new ArrayList<>();
        userController.add(new Method( "/user/find/{username}", "user/find/iu@naver.com","GET", "username을 통한 사용자 정보 조회", "ex) username = iu@naver.com"));
        userController.add(new Method("/user/{nickname}", "user/아이유", "GET", "nickname 을 통한 사용자 정보 조회 (한명, 전부 일치)", "ex) nickname = 아이유"));
        userController.add(new Method( "/user/{nickname}/check", "user/아이유/check","GET", "nickname 중복 여부 판단 (true - 사용가능한 닉네임)", "ex) nickname = 아이유"));
        userController.add(new Method( "/user/like/{nickname}", "user/like/이","GET", "nickname 값을 갖고 있는 모든 사용자의 정보를 조회", "ex) nickname = 이"));
        userController.add(new Method("/user/all", "user/all","GET", "모든 사용자 정보 조회", "") );
        userController.add(new Method("/user", " ", "POST", "사용자 정보 등록", "\"username\":\"필수\", \"password\":\"필수\", \"nickname\":\"필수\", \"birthday\":\"필수\", \"gender\":\"MALE\" or \"FEMALE\""));
        userController.add(new Method( "/user/id/check", " ","POST", "username 중복 여부 판단 (true - 사용가능한 닉네임)", "\"username\":\"필수\""));
        userController.add(new Method("/user/login", " ", "POST", "사용자 로그인 (JWT)", "\"username\":\"필수\", \"password\":\"필수\""));
        userController.add(new Method("/user/detail/{oldNickname}", " ", "PUT", "사용자 세부 정보 등록", " oldNickname <- 이전 닉네임,\"nickname\":\"필수\", \"profileImgURL\": \" \", \"introduce\": \" \""));

        // Music 부분 API URL
        List<Method> musicController = new ArrayList<>();
        musicController.add(new Method( "/music/keyword/{keyword}", "music/keyword/Kanye","GET", "제목과 가수중 'keyword'가 들어간 곡들 모두 정보 조회", "ex) keyword = Kanye"));
        musicController.add(new Method( "/music/title/{title}", "music/title/Jail","GET", "title을 제목으로 포함한 모든 곡 정보 조회", "ex) title = Jail"));
        musicController.add(new Method( "/music/title/{title}/one", "music/title/Jail/one","GET", "곡 정보 조회", "ex) title = Jail"));
        musicController.add(new Method( "/music/all", "music/all","GET", "모든 곡 정보 조회", ""));
        musicController.add(new Method( "/music", "music","POST", "곡 추가", "\"title\": \"필수\", \"singer\": \"필수\", \"profileImgURL\":\" \", \"lyrics\":\" \""));

        // Team 부분 API URL
        List<Method> teamController = new ArrayList<>();
        teamController.add(new Method( "/team/{teamName}", "team/1번팀","GET", "teamName 으로 팀 조회", "ex) teamName = 1번 팀"));
        teamController.add(new Method( "/team/all", "team/all","GET", " 모든 팀 조회", " "));
        teamController.add(new Method( "/team/onAir", " ","POST", "onAir 필드를 True or False로 변경", "teamName:'필수'"));
        teamController.add(new Method( "/team", " ","POST", "팀 추가", "\"teamName\":\"1번팀\", \"introduce\":\"\", \"leaderName\":\"아이유\""));
        teamController.add(new Method( "/team/{teamName}", " ","PUT", "팀 정보 변경", "ex) teamName = 1번팀 / \"teamName\":\"1번팀\", \"introduce\":\"\", \"leaderName\":\"아이유\" "));

        // Room 부분 API
        List<Method> roomController = new ArrayList<>();
        roomController.add(new Method( "/room/all", "room/all","GET", "생성 되어있는 방 전체 조회", ""));
        roomController.add(new Method( "/room/{roomName}/{teamName}", " ","GET", "방 이름/팀 이름으로 방 정보 조회", "ex) roomName = 1번 방, teamName = 1번팀  ※Test data 없음"));
        roomController.add(new Method( "/room", " ","POST", "방 생성", "\"roomName\":\"1번방(필수)\", \"teamName\":\"1번팀(필수)\", \"introduce\":\"안녕하세요 1번 방입니다.\""));
        roomController.add(new Method( "/room/{roomName}/{teamName}/music", " ","POST", "방에 뮤직 예약 등록", " ex) roomName = 1번 방, teamName = 1번팀 / \"title\": \"필수\", \"singer\": \"필수\", \"profileImgURL\":\" \", \"lyrics\":\" \""));
        roomController.add(new Method( "/room/{roomName}/{teamName}/music", " ","DELETE", "방에 예약되어 있는 노래 삭제", " ex) roomName = 1번 방, teamName = 1번팀 / \"title\": \"필수\", \"singer\": \"필수\", \"profileImgURL\":\" \", \"lyrics\":\" \""));
        roomController.add(new Method( "/room", " ","DELETE", "방 삭제", "\"roomName\":\"1번방(필수)\", \"teamName\":\"1번팀(필수)\", \"introduce\":\"안녕하세요 1번 방입니다.\""));


        // Controller MAP
        Map<String, List<Method>> methodList =new HashMap<>();
        methodList.put("User",userController);
        methodList.put("Music",musicController);
        methodList.put("Team", teamController);
        methodList.put("Room", roomController);

        model.addAttribute("methods", methodList);


        return "thymeleaf/hello";
    }

}
