package com.example.demo.user;


import com.example.demo.ResponseDto;
import com.example.demo.config.auth.jwt.JwtTokenProvider;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserApiController {
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder encoder;
    private final UserService userService;
    private final UserRepository userRepository;


    @PostMapping("/login")
    public String login(@RequestBody Map<String, String> user) {
        User member = userRepository.findByUsername(user.get("username")).orElseThrow(
                () -> new IllegalArgumentException("없는 아이디 입니다."));
        if (!encoder.matches(user.get("password"), member.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호 입니다.");
        }
        return jwtTokenProvider.createToken(member.getUsername(), member.getRole());
    }

    @GetMapping("/{username}")
    public ResponseDto<User> userInfo(@PathVariable String username) {
        return new ResponseDto<User>(HttpStatus.OK.value(), userService.userFindByUsername(username));
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
