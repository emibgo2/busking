package com.example.demo.user;


import com.example.demo.Gender;
import com.example.demo.ResponseDto;
import com.example.demo.RoleType;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserApiController {

    private UserService userService;
    private UserRepository userRepository;


    @GetMapping("/{loginEmail}")
    public ResponseDto<User> userInfo(@PathVariable String loginEmail) {
        return new ResponseDto<User>(HttpStatus.OK.value(), userService.userFindByLoginEmail(loginEmail));
    }

    @GetMapping("/all")
    public ResponseDto<List> userAllInfo() {
        List<User> all = userRepository.findAll();
        for (int i = 0; i < all.size(); i++) {
            all.get(i).setId(i + 1L);
        }
        return new ResponseDto<List>(HttpStatus.OK.value(), userRepository.findAll());
    }


    @PostMapping("/id/check")
    public ResponseDto<Integer> idCheck(@RequestBody User user) {

        return new ResponseDto<Integer>(HttpStatus.OK.value(), userService.checkMemberId(user));
        // 유저 회원가입 메소드
    }

    @PostMapping
    public int save(@Valid @RequestBody User user) {
        log.info("Sign Up User ={}", user);
        return userService.joinMember(user, 1);
        // 유저 회원가입 메소드
    }

    @DeleteMapping("/all")
    public int delete() {
        return userService.deleteTestDataAfter();
    }

    // Role Type 1: User, Role Type 2:ADMIN
    @PostMapping("/admin/joinProc")
    public ResponseDto<Integer> admin_Save(@RequestBody User user) {
        return new ResponseDto<Integer>(HttpStatus.OK.value(), userService.joinMember(user, 2));

        // 관리자 회원가입 메소드
    }






//   기존 로그인 방식
//    @PostMapping("/api/user/login")
//    public ResponseDto<Integer> login(@RequestBody User user, HttpSession session) {
//        System.out.println("UserApiController: login 호출됨");
//        User principal = userService.login(user);
//        if (principal != null) {
//            session.setAttribute("principal",principal);
//        }
//        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
//    }

}
