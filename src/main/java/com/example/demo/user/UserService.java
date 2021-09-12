package com.example.demo.user;


import com.example.demo.RoleType;
import com.example.demo.TestData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserService {



    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;



    @Transactional(readOnly = true)
    public int checkMemberId(User requestUser) {
        User user= userRepository.findByLoginEmail(requestUser.getLoginEmail()).orElseGet(() -> {
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
    public User userFindByLoginEmail(String username){
        return userRepository.findByLoginEmail(username)
                .orElseGet(() -> {
                    return new User();
                });
        // 해당 id값에 해당하는 Storage를 Return
    }

    public Long clearDB(int requestId) {
        int result=0;
        if (requestId>=1)  result= requestId*10+5-10;
        return new Long(result);
    }

    public Long clearDB(Long requestId) {
        Long result=0L;
        if (requestId>=1)  result= requestId*10L+5L-10L;
        return result;
    }
}
