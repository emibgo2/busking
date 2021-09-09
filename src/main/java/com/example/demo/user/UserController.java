package com.example.demo.user;

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
public class UserController {
    @GetMapping({"/"})
    public String index(Model model) {
        // UserController 부분 API URL
        List<Method> userController = new ArrayList<>();
        userController.add(new Method( "/user/{loginID}", "user/아이유","GET", "사용자 정보 조회", "ex) loginID = 아이유"));
        userController.add(new Method("/user/all", "user/all","GET", "모든 사용자 정보 조회", "") );
        userController.add(new Method("/user", " ", "POST", "사용자 정보 등록", "loginID : ' ', password : ' '"));
        userController.add(new Method("/user/all", " ", "POST", "사용자 정보 등록", ""));


        // Controller MAP
        Map<String, List<Method>> methodList =new HashMap<>();
        methodList.put("User",userController);
        model.addAttribute("methods", methodList);

        return "thymeleaf/hello";
    }

}
