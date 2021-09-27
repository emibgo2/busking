package com.example.demo.user;


import com.example.demo.Gender;
import com.example.demo.RoleType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import sun.security.util.Password;

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
    public List<User> userList = new ArrayList<>();

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
    public int joinMember(@Validated User user, int roleType) {
        int checkResult = checkMemberId(user);
        if (checkResult == 1) {
            String rawPassword = user.getPassword(); // 원문
            String encPassword = encoder.encode(rawPassword);
            user.setPassword(encPassword);
            if (roleType == 1) user.setRole(RoleType.USER);
            else if (roleType == 2) user.setRole(RoleType.ADMIN);
            try {
                userRepository.save(user);
            }catch (Exception e){
                log.error("error! ");
                return HttpStatus.INTERNAL_SERVER_ERROR.value();
            }
            return HttpStatus.CREATED.value();
            // User의 정보와 비밀번호를 해쉬한 값을 DB에 저장
        }else return HttpStatus.CONFLICT.value();

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


    @PostConstruct
    public void init() {
        /**
         *  User Test Data
         */


        userList.add(new User("iu@naver.com","1","아이유",29, Gender.FEMALE, RoleType.ADMIN,"https://image.genie.co.kr/Y/IMAGE/IMG_ARTIST/067/872/918/67872918_1616652768439_20_600x600.JPG", Timestamp.valueOf(LocalDateTime.now())));
        userList.add(new User("heize@naver.com", "1","헤이즈",31,Gender.FEMALE,RoleType.USER, "https://i1.sndcdn.com/artworks-000324021660-jgzmbq-t500x500.jpg", Timestamp.valueOf(LocalDateTime.now())));
        userList.add(new User("han@naver.com","1","한서희",27,Gender.FEMALE,RoleType.USER,"https://i.pinimg.com/originals/c0/da/57/c0da57e76bde0ccc9fc503bb3f77d217.jpg", Timestamp.valueOf(LocalDateTime.now())));
        userList.add(new User("default@naver.com","1","디폴트사용자",20,Gender.MALE,RoleType.USER,null, Timestamp.valueOf(LocalDateTime.now())));

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
