package com.example.demo.user;


import com.example.demo.ResponseDto;
import com.example.demo.config.auth.jwt.JwtDto;
import com.example.demo.config.auth.jwt.JwtTokenProvider;
import com.example.demo.user.userDetail.UserDetailDto;
import com.example.demo.user.userDetail.UserDetailRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private final UserDetailRepository userDetailRepository;


    @PostMapping("/login")
    public ResponseEntity<JwtDto> login(@RequestBody Map<String, String> loginDto) {

        String username = loginDto.get("username");
        String password = loginDto.get("password");
        if (username == null || password == null) {
            return new ResponseEntity("아이디 패스워드 모두 입력해야 합니다", HttpStatus.BAD_REQUEST);
        }

        User user = userService.login(username, password);
        if (user == null) {
            return new ResponseEntity("아이디 혹은 패스워드를 다시 확인하십시오", HttpStatus.FORBIDDEN);
        }
        return ResponseEntity.ok(new JwtDto(jwtTokenProvider.createToken(user.getNickname(), user.getRole()), user.getNickname(), user.getTeam().getTeamName()));
    }

    @GetMapping("/find/{username}")
    public ResponseEntity<UserDto> userInfo(@PathVariable String username) {


        User user = userService.userFindByUsername(username);
        if (user == null) {
            return new ResponseEntity(new UserDto(),HttpStatus.NO_CONTENT );
        }
        UserDto userDto = UserDto.builder()
                .birthday(user.getBirthday())
                .gender(user.getGender())
                .nickname(user.getNickname())
                .userDetail(user.userToDto().getUserDetail())
                .build();
        return new ResponseEntity<>(userDto,HttpStatus.OK);
    }

    @GetMapping("/{nickname}") // 유저 한명 특정
    public ResponseEntity<UserDto> userFindByNickName(@PathVariable String nickname) {
        User user = userService.userFindByNickname(nickname);
        if (user == null) {
            return ResponseEntity.noContent().build();

        }
        UserDto userDto = UserDto.builder()
                .birthday(user.getBirthday())
                .gender(user.getGender())
                .nickname(user.getNickname())
                .userDetail(user.userToDto().getUserDetail())
                .build();
        return ResponseEntity.ok(userDto);
    }

    @GetMapping("/like/{nickname}") // nickname에 포함된 글자를 토대로 검색
    public ResponseEntity<List> userContainByNickName(@PathVariable String nickname) {

        return ResponseEntity.ok(userService.userContainsByNickname(nickname));
    }


    @GetMapping("/all")
    public List<UserDto> userAllInfo() {
        return userService.getAllDto();
    }


    @PostMapping("/id/check")
    public ResponseEntity<Boolean> idCheck(@RequestBody User user) {
        return ResponseEntity.ok(userService.checkMemberId(user));
        // 유저 회원가입 메소드
    }

    @GetMapping("/{nickname}/check")
    public ResponseEntity<Boolean> nicknameCheck(@PathVariable String nickname ) {
        return ResponseEntity.ok(userService.checkNickname(nickname));
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
    public ResponseEntity<Integer> admin_Save(@RequestBody User user) {
        return ResponseEntity.ok(userService.joinMember(user, 2));
        // 관리자 회원가입 메소드
    }

    // UserDetail 부분

    @PutMapping("/detail/{oldNickname}")
    public UserDetailDto userDetailSave(@PathVariable String oldNickname, @RequestBody UserDetailDto userDetailDto) {
        userService.detailEdit(oldNickname, userDetailDto);
        return userDetailDto;
    }

}





