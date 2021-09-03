package com.example.demo.user;


import com.example.demo.ResponseDto;
import com.example.demo.RoleType;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserApiController {

    private UserService userService;
    private UserRepository userRepository;

    @GetMapping("/{userName}")
    public ResponseDto<User> userInfo(@PathVariable String userName) {

        return new ResponseDto<User>(HttpStatus.OK.value(), userService.userFindByUsername(userName));
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

//        User adminCheck = userRepository.findByUsername("아이유").orElseGet(() -> {
//            return new User();
//        });
//        if (adminCheck.getUsername() == null) {
//            User admin = new User("아이유","1","https://image.genie.co.kr/Y/IMAGE/IMG_ARTIST/067/872/918/67872918_1616652768439_20_600x600.JPG");
//            userService.joinMember(admin, 2);
//            log.info("관리자 아이디 생성");
//        } else log.info("이미 관리자 아이디가 있습니다.");
//        User userCheck = userRepository.findByUsername("헤이즈").orElseGet(() -> {
//            return new User();
//        });
//        if (userCheck.getUsername() == null) {
//            User user = new User("헤이즈","1","https://image.genie.co.kr/Y/IMAGE/IMG_ARTIST/067/872/918/67872918_1616652768439_20_600x600.JPG");
//            userService.joinMember(user, 1);
//            log.info("관리자 아이디 생성");
//        } else log.info("이미 관리자 아이디가 있습니다.");

        List<User> userList = new ArrayList<>();
        userList.add(new User("아이유","1","https://image.genie.co.kr/Y/IMAGE/IMG_ARTIST/067/872/918/67872918_1616652768439_20_600x600.JPG"));
        userList.add(new User("헤이즈", "1", "https://i1.sndcdn.com/artworks-000324021660-jgzmbq-t500x500.jpg"));
        userList.add(new User("한서희","1","https://i.pinimg.com/originals/c0/da/57/c0da57e76bde0ccc9fc503bb3f77d217.jpg"));
        userList.add(new User("default","1",""));


        for (User user : userList) {
            User Check = userRepository.findByUsername(user.getUsername()).orElseGet(() -> {
                return new User();
            });
            if (user.getUsername()=="아이유" && Check.getUsername() == null ){
                userService.joinMember(user, 2);
                log.info("ADMIN 아이디 생성");
            }
            else if (Check.getUsername() == null) {
                userService.joinMember(user, 1);
                log.info("User 아이디 생성");
            }
            else log.info("이미 관리자 아이디가 있습니다.");
        }


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
