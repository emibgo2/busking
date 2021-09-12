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
         * 사용법
         * 1. List<Method> 모델이름Controller 으로 만들것
         * 2. 각자 컨트롤러에 해당하는 Method( URL 정보 ) 를 삽입
         ★★★★!!!중요!!!★★★★
         add() 되는 곳이 어디인지 확인하기
         * 3. 이후 methodList에 put("모델이름", 모델이름Controller ) 넣기!
         * 끝
         */

        // UserController 부분 API URL
        List<Method> userController = new ArrayList<>();
        userController.add(new Method( "/user/{loginEmail}", "user/iu@naver.com","GET", "사용자 정보 조회", "ex) loginEmail = iu@naver.com"));
        userController.add(new Method("/user/all", "user/all","GET", "모든 사용자 정보 조회", "") );
        userController.add(new Method("/user", " ", "POST", "사용자 정보 등록", "loginEmail : ' ', password : ' '"));

        // Music 부분 API URL
        List<Method> musicController = new ArrayList<>();
        musicController.add(new Method( "/music/{title}", "music/Jail","GET", "곡 정보 조회", "ex) title = Jail"));
        musicController.add(new Method( "/music/all", "music/all","GET", "모든 곡 정보 조회", ""));


        // Controller MAP
        Map<String, List<Method>> methodList =new HashMap<>();
        methodList.put("User",userController);
        methodList.put("Music",musicController);


        model.addAttribute("methods", methodList);


        return "thymeleaf/hello";
    }

}
