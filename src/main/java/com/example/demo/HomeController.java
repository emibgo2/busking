package com.example.demo;

import com.example.demo.Method;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
        userController.add(new Method( "/user/{nickname}", "user/이","GET", "nickname을 통한 사용자 정보 조회", "ex) nickname = 이"));
        userController.add(new Method("/user/all", "user/all","GET", "모든 사용자 정보 조회", "") );
        userController.add(new Method("/user", " ", "POST", "사용자 정보 등록", "\"username\":\"필수\", \"password\":\"필수\", \"nickname\":\"필수\", \"birthday\":\"필수\", \"gender\":\"MALE\" or \"FEMALE\""));
        userController.add(new Method("/user/login", " ", "POST", "사용자 로그인 (JWT)", "\"username\":\"필수\", \"password\":\"필수\""));
        userController.add(new Method("/user/detail", " ", "PUT", "사용자 세부 정보 등록", " \"nickname\":\"필수\", \"profileImgURL\": \" \", \"introduce\": \" \""));

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

        
        // Controller MAP
        Map<String, List<Method>> methodList =new HashMap<>();
        methodList.put("User",userController);
        methodList.put("Music",musicController);
        methodList.put("Team",teamController);

        model.addAttribute("methods", methodList);


        return "thymeleaf/hello";
    }

}
