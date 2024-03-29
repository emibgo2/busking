package com.example.demo.user;


import com.example.demo.team.Team;
import com.example.demo.team.TeamRepository;
import com.example.demo.team.TeamSaveForm;
import com.example.demo.user.userDetail.UserDetail;
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
import javax.annotation.PreDestroy;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserService {



    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final UserDetailRepository userDetailRepository;
    private final TeamRepository teamRepository;
    public   List<User> userList = new ArrayList<>();

    @Transactional(readOnly = true)
    public boolean checkMemberId(User requestUser) {
        User user= userRepository.findByUsername(requestUser.getUsername()).orElseGet(() -> {
            return new User();
        });

        if (user.getPassword() == null) {
            return true;
            // 해당 아이디 사용가능
        }
        else{
            return false;}
    }
    @Transactional(readOnly = true)
    public User login(String username,String password) {
        User user = userRepository.findByUsername(username).orElseGet(() -> null);
        if (user == null ||!encoder.matches(password, user.getPassword())) {
            return null;
        }

        return user;
    }

    @Transactional(readOnly = true)
    public boolean checkNickname(String nickname) {
        User user= userRepository.findByNickname(nickname).orElseGet(() -> {
            return new User();
        });

        if (user.getPassword() == null) {

            return true;
            // 해당 아이디 사용가능
        }
        else{
            return false;}

    }

    @Transactional
    public void joinTeam(String userNickname, TeamSaveForm team) {
        User user= userRepository.findByNickname(userNickname).orElseGet(() -> {
            return new User();
        });
        Team findTeam = teamRepository.findByTeamName(team.getTeamName())
                .orElseThrow(() -> {
                    return new IllegalArgumentException("error! 팀이 없습니다");
                });
        user.setTeam(findTeam);
        findTeam.getUserList().add(user);
    }
    @Transactional
    public int joinMember(@Validated User user, int roleType ) {
        if (roleType ==0) roleType=1; // default 값 1

        if (checkMemberId(user)) {
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
            //User가 있는 지 먼저 확인
            return new IllegalArgumentException("유저를 찾을 수 없습니다.");
        });
        if (user.getUserDetail() == null) {
            userDetailRepository.mSave(user.getId(), userDetailDto.getProfileImgURL(), userDetailDto.getIntroduce());
        } else {
            user.getUserDetail().setIntroduce(userDetailDto.getIntroduce());
            user.getUserDetail().setProfileImgURL(userDetailDto.getProfileImgURL());
        }

    }
    @Transactional
    public void  detailEdit(String oldNickname,UserDetailDto userDetailDto) {
        User user= userRepository.findByNickname(oldNickname).orElseThrow(() -> {
            //User가 있는 지 먼저 확인
            return new IllegalArgumentException("유저를 찾을 수 없습니다.");
        });
        if (user.getUserDetail() == null) {
            userDetailRepository.mSave(user.getId(), userDetailDto.getProfileImgURL(), userDetailDto.getIntroduce());
        } else {
            user.setNickname(userDetailDto.getNickname());
            user.getUserDetail().setIntroduce(userDetailDto.getIntroduce());
            user.getUserDetail().setProfileImgURL(userDetailDto.getProfileImgURL());
        }

    }


    @Transactional
    public int userDeleteById(Long id) {
        userRepository.deleteById(id);
        return HttpStatus.OK.value();
    }

    @Transactional
    public int deleteTestDataAfter() {

        List<UserDetail> userDetailList = new ArrayList<>();

        // 테스트 데이터를 위한 메소드 ( 메모리 낭비 및 비효율 ! )
        userDetailList.add(new UserDetail(userList.get(0), "https://www.theguru.co.kr/data/photos/20210937/art_16316071303022_bf8378.jpg", "안녕하세요 아이유 입니다."));
        userDetailList.add(new UserDetail(userList.get(1), "https://file.mk.co.kr/meet/neds/2021/02/image_readtop_2021_188127_16142386024553959.jpg", "안녕하세요 헤이즈 입니다."));
        userDetailList.add(new UserDetail(userList.get(2), "https://i.pinimg.com/originals/c0/da/57/c0da57e76bde0ccc9fc503bb3f77d217.jpg", "안녕하세요 한서희 입니다."));
        userDetailList.add(new UserDetail(userList.get(3), null, "안녕하세요"));

        for (Long i =  userRepository.findFirstByOrderByIdDesc().get().getId(); i > userList.size(); i--) {
            User user = userRepository.findById(i).orElseGet(() -> {
                return new User();
            });
            if (user.getUsername() == null) continue;
            if (user.getTeam() != null) {
                return 50;
            }else userRepository.deleteById(i);
            log.info("{} 번 user가 삭제되었습니다.",i);
        }
        for (int i = 0; i < userList.size(); i++) {
            System.out.println("userSize = " + i);

        }
        for (Long i = 1L; i < userList.size()-1; i++) {
            System.out.println("i = " + i);
            User user = userRepository.findById(i).orElseThrow(() -> {
                return new IllegalArgumentException("User가 없다");
            });
            if (i < 5) {
                user.setNickname(userList.get(i.intValue() -1).getNickname());
                user.getUserDetail().setProfileImgURL(userDetailList.get(i.intValue()-1).getProfileImgURL());
                user.getUserDetail().setIntroduce(userDetailList.get(i.intValue()-1).getIntroduce());
            }

        }
//        for (Long i = 1L; i <= userList.size(); i++) {
//            System.out.println("i = " + i);
//            userList.get(i.intValue()).setUserDetail(userDetailList.get(i.intValue() -1));
//
//        }
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
                .orElseGet(() -> null);
        // 해당 id값에 해당하는 Storage를 Return
    }

    @Transactional(readOnly = true)
    public User userFindByNickname(String nickname){
        return userRepository.findByNickname(nickname)
                .orElseGet(() -> null);
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

    @Transactional(readOnly = true)
    public List<UserDto> getAllDto() {
       return userRepository.findAll().stream()
                .map(
                        user ->
                                UserDto.builder()
                                        .birthday(user.getBirthday())
                                        .gender(user.getGender())
                                        .nickname(user.getNickname())
                                        .userDetail(user.userToDto().getUserDetail())
                                        .build()
                ).collect(Collectors.toList());
    }
    @Transactional
    public void testUser() {
        userList.add(new User("iu@naver.com", "1", "아이유", 1993, Gender.FEMALE,Timestamp.valueOf(LocalDateTime.now())));
        userList.add(new User("heize@naver.com", "1","헤이즈",1991,Gender.FEMALE, Timestamp.valueOf(LocalDateTime.now())));
        userList.add(new User("han@naver.com","1","한서희",1995,Gender.FEMALE, Timestamp.valueOf(LocalDateTime.now())));
        userList.add(new User("default@naver.com","1","디폴트사용자",2003,Gender.MALE, Timestamp.valueOf(LocalDateTime.now())));
        userList.add(new User("ryuyh2000@naver.com","1","유영하",2000,Gender.MALE, Timestamp.valueOf(LocalDateTime.now())));
        userList.add(new User("gbwlxhd97@naver.com","1","이지원",2000,Gender.MALE, Timestamp.valueOf(LocalDateTime.now())));
        userList.add(new User("emibgo@naver.com","1","고지훈",1997,Gender.MALE, Timestamp.valueOf(LocalDateTime.now())));
        userList.add(new User("kdj38245@naver.com","1","김동주",2002,Gender.MALE, Timestamp.valueOf(LocalDateTime.now())));
        userList.add(new User("5543705@naver.com","1","이채린",2002,Gender.FEMALE, Timestamp.valueOf(LocalDateTime.now())));

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
    @PostConstruct
    public void init() {
        testUser();
    }

    @PreDestroy
    public void Test() {

    }



}

