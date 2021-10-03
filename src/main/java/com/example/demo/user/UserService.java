package com.example.demo.user;


import com.example.demo.user.userDetail.UserDetailDto;
import com.example.demo.user.userDetail.UserDetailRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.PostConstruct;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserService {



    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final UserDetailRepository userDetailRepository;
    public  List<User> userList = new ArrayList<>();

    @Transactional(readOnly = true)
    public int checkMemberId(User requestUser) {
        User user= userRepository.findByUsername(requestUser.getUsername()).orElseGet(() -> {
            return new User();
        });
        System.out.println(user);
        if (user.getPassword() == null) {
            System.out.println("result: "+ 1);
            return 1;
            // 해당 아이디 사용가능
        }
        else{             System.out.println("result: "+ 2);
            return 2;}

    }

    @Transactional
    public int joinMember(@Validated User user, int roleType ) {
        if (roleType ==0) roleType=1; // default 값 1

        int checkResult = checkMemberId(user);
        if (checkResult == 1) {
            String rawPassword = user.getPassword(); // 원문
            String encPassword = encoder.encode(rawPassword);
            user.setPassword(encPassword);
            if (roleType == 1) user.setRole(RoleType.USER);
            else if (roleType == 2) user.setRole(RoleType.ADMIN);
            try {
                userRepository.save(user);
            } catch (Exception e) {
                log.error("error! ={} ", e.getMessage());
                return HttpStatus.INTERNAL_SERVER_ERROR.value();
            }
            return HttpStatus.CREATED.value();
            // User의 정보와 비밀번호를 해쉬한 값을 DB에 저장
        } else {
            log.info("Sign Up! User = {}",user);
            return HttpStatus.CONFLICT.value();
        }
    }

    @Transactional
    public void  detailSave(UserDetailDto userDetailDto) {
        User user= userRepository.findByNickname(userDetailDto.getNickname()).orElseThrow(() -> {
            return new IllegalArgumentException("유저를 찾을 수 없습니다.");
        });
        // user가 있는지 먼저 확인
        userDetailRepository.mSave(user.getId(), userDetailDto.getProfileImgURL(), userDetailDto.getIntroduce());
    }


    @Transactional
    public int userDeleteById(Long id) {
        userRepository.deleteById(id);
        return HttpStatus.OK.value();
    }

    @Transactional
    public int deleteTestDataAfter() {
        for (Long i =  userRepository.count(); i > 4; i--) {
            userRepository.deleteById(i);
            log.info("{} 번 user가 삭제되었습니다.",i);
        }
        return HttpStatus.OK.value();
    }

    @Transactional(readOnly = true)
    public User userFindById(Long id){
        return userRepository.findById(id)
                .orElseGet(() -> {
                    return new User();
                });
        // 해당 id값에 해당하는 Storage를 Return
    }

    @Transactional(readOnly = true)
    public User userFindByUsername(String username){
        return userRepository.findByUsername(username)
                .orElseGet(() -> {
                    return new User();
                });
        // 해당 id값에 해당하는 Storage를 Return
    }

    @Transactional(readOnly = true)
    public List<User> userContainsByNickname(String username){
        return userRepository.findByNicknameContains(username)
                .orElseGet(() -> {
                    return new ArrayList<>();
                });
        // 해당 id값에 해당하는 Storage를 Return
    }

    @PostConstruct
    public void init() {
        userList.add(new User("iu@naver.com", "1", "아이유", 1993, Gender.FEMALE,Timestamp.valueOf(LocalDateTime.now())));
        userList.add(new User("heize@naver.com", "1","헤이즈",1991,Gender.FEMALE, Timestamp.valueOf(LocalDateTime.now())));
        userList.add(new User("han@naver.com","1","한서희",1995,Gender.FEMALE, Timestamp.valueOf(LocalDateTime.now())));
        userList.add(new User("default@naver.com","1","디폴트사용자",2003,Gender.MALE, Timestamp.valueOf(LocalDateTime.now())));

        for (User user : userList) {
            User Check = userRepository.findByUsername(user.getUsername()).orElseGet(() -> {
                return new User();
            });
            if (user.getUsername()=="iu@naver.com" && Check.getUsername() == null ){

                joinMember(user, 2);
                log.info("ADMIN 아이디 생성");
            }
            else if (Check.getUsername() == null) {
                joinMember(user, 1);
                log.info("User 아이디 생성");
            }
            else log.info("이미 아이디가 있습니다.");
        }
    }

}
