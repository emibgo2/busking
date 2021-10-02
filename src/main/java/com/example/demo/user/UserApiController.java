package com.example.demo.user;


import com.example.demo.ResponseDto;
import com.example.demo.config.auth.jwt.JwtTokenProvider;
import com.example.demo.user.userDetail.UserDetailDto;
import com.example.demo.user.userDetail.UserDetailRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
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
    private final UserDetailRepository userDetailRepository;


    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> user) {
        User member = userRepository.findByUsername(user.get("username")).orElseThrow(
                () -> new IllegalArgumentException("없는 아이디 입니다."));
        if (!encoder.matches(user.get("password"), member.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호 입니다.");
        }
        Map<String, String>jwtDto = new HashMap<>();
        jwtDto.put("token", jwtTokenProvider.createToken(member.getUsername(), member.getRole()));
        jwtDto.put("username", member.getNickName());
        return  jwtDto;
    }

    @GetMapping("/{username}")
    public ResponseDto<User> userInfo(@PathVariable String username) {
//        List<UserDetail> userDetailList = new ArrayList<>();
//        User user = userService.userFindByUsername(username);
//        // 테스트 데이터를 위한 메소드 ( 메모리 낭비 및 비효율 ! )
//        userDetailList.add(new UserDetail( null, "https://image.genie.co.kr/Y/IMAGE/IMG_ARTIST/067/872/918/67872918_1616652768439_20_600x600.JPG", "안녕하세요 아이유 입니다."));
//        userDetailList.add(new UserDetail( null, "https://i1.sndcdn.com/artworks-000324021660-jgzmbq-t500x500.jpg", "안녕하세요 헤이즈 입니다."));
//        userDetailList.add(new UserDetail( null, "https://i.pinimg.com/originals/c0/da/57/c0da57e76bde0ccc9fc503bb3f77d217.jpg", "안녕하세요 한서희 입니다."));
//        userDetailList.add(new UserDetail(null, null, "안녕하세요"));
//        int count =1;
//        for (UserDetail userDetail : userDetailList) {
//            System.out.println(new UserDetailDto(new Long (count), userDetail.getProfileImgURL(), userDetail.getIntroDuce()));
//            userService.detailSave(new UserDetailDto(new Long (count), userDetail.getProfileImgURL(), userDetail.getIntroDuce()));
//            count++;
//        }

        return new ResponseDto<User>(HttpStatus.OK.value(),userService.userFindByUsername(username));
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

    // UserDetail 부분

    @PutMapping("/detail")
    public ResponseDto<Integer> userDetailSave(@RequestBody UserDetailDto userDetailDto ) {
        System.out.println("userDetailDto = " + userDetailDto);
        userService.detailSave(userDetailDto);
        return new ResponseDto<Integer>(HttpStatus.OK.value(),1/* 아직 미정 */);

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
