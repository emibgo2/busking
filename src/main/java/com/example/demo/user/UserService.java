package com.example.demo.user;


import com.example.demo.RoleType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class UserService {


    @Autowired
    private UserRepository userRepository;



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
    public int joinMember(User user,int roleType) {
        int checkResult = checkMemberId(user);
        if (checkResult == 1) {
            String rawPassword = user.getPassword(); // 원문
            String encPassword =(rawPassword);
            user.setPassword(encPassword);
            if (roleType == 1) user.setRole(RoleType.USER);
            else if (roleType == 2) user.setRole(RoleType.ADMIN);
            try {
                userRepository.save(user);
            }catch (Exception e){
                return 3;
            }
            return 1;
            // User의 정보와 비밀번호를 해쉬한 값을 DB에 저장
        }else return 2;

    }

    @Transactional(readOnly = true)
    public User userDetail(int id){
        return userRepository.findById(id)
                .orElseGet(() -> {
                    return new User();
                });
        // 해당 id값에 해당하는 Storage를 Return
    }




}
