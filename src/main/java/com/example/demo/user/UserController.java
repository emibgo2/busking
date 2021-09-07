package com.example.demo.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {
    @GetMapping({"/"})
    public String index() { // 컨트롤러에서 세션 접근
        return "thymeleaf/hello";
    }

}
