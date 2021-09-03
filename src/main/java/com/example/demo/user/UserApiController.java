package com.example.demo.user;


import com.example.demo.ResponseDto;
import com.example.demo.RoleType;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class UserApiController {

    private UserService userService;
    private UserRepository userRepository;

    @GetMapping("/user/{userId}")
    public ResponseDto<User> userInfo(@PathVariable int userId) {

        return new ResponseDto<User>(HttpStatus.OK.value(), userService.userDetail(userId));

    }


    @PostMapping("/id/check")
    public ResponseDto<Integer> idCheck(@RequestBody User user) {

        return new ResponseDto<Integer>(HttpStatus.OK.value(), userService.checkMemberId(user));
        // 유저 회원가입 메소드
    }

    @PostMapping("/joinProc")
    public ResponseDto<Integer> save(@RequestBody User user) {
        return new ResponseDto<Integer>(HttpStatus.OK.value(), userService.joinMember(user, 1));
        // 유저 회원가입 메소드
    }

    // Role Type 1: User, Role Type 2:ADMIN
    @PostMapping("/admin/joinProc")
    public ResponseDto<Integer> admin_Save(@RequestBody User user) {
        return new ResponseDto<Integer>(HttpStatus.OK.value(), userService.joinMember(user, 2));

        // 관리자 회원가입 메소드
    }

    @PostConstruct
    public void init() {

        User adminCheck = userRepository.findByUsername("admin").orElseGet(() -> {
            return new User();
        });
        if (adminCheck.getUsername() == null) {
            User admin = new User("아이유","1", RoleType.ADMIN,"https://image.genie.co.kr/Y/IMAGE/IMG_ARTIST/067/872/918/67872918_1616652768439_20_600x600.JPG");
            userService.joinMember(admin, 2);
            log.info("관리자 아이디 생성");
        } else log.info("이미 관리자 아이디가 있습니다.");


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
