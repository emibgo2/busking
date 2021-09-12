package com.example.demo;

import com.example.demo.music.Music;
import com.example.demo.music.MusicRepository;
import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
import com.example.demo.user.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@AllArgsConstructor
public class TestData {

    private UserRepository userRepository;
    private UserService userService;
    private MusicRepository musicRepository;
    public static final List<User> userList = new ArrayList<>();

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
            User Check = userRepository.findByLoginEmail(user.getLoginEmail()).orElseGet(() -> {
                return new User();
            });
            if (user.getLoginEmail()=="iu@naver.com" && Check.getLoginEmail() == null ){
                userService.joinMember(user, 2);
                log.info("ADMIN 아이디 생성");
            }
            else if (Check.getLoginEmail() == null) {
                userService.joinMember(user, 1);
                log.info("User 아이디 생성");
            }
            else log.info("이미 아이디가 있습니다.");
        }


        /**
         *  Music Test Data
         */

        List<Music> MusicList = new ArrayList<>();
        MusicList.add(new Music("Jail", "Kanye West", "https://w.namu.la/s/3d07a08af0b3be7afeee94f2972ebc72241d2cc9f6ce06f5dc2cbff51d19f9184c69172d0e776cc70a0de6de927e86a409eb43843a4040657d0c8dcc2a59cdb9f932608fdb59d5cc041c55e0a2ec340f9ce77a9f9f4933e2250b1eedec7fdbfc"));
        MusicList.add(new Music("Jail Pt.2", "Kanye West", "https://w.namu.la/s/3d07a08af0b3be7afeee94f2972ebc72241d2cc9f6ce06f5dc2cbff51d19f9184c69172d0e776cc70a0de6de927e86a409eb43843a4040657d0c8dcc2a59cdb9f932608fdb59d5cc041c55e0a2ec340f9ce77a9f9f4933e2250b1eedec7fdbfc"));
        MusicList.add(new Music("Circles", "Post Malone", "http://image.yes24.com/goods/79640397/XL"));

        for (Music music : MusicList) {
            Music Check = musicRepository.findByTitle(music.getTitle()).orElseGet(() -> {
                return new Music();
            });
            if (Check.getTitle() == null) {
                musicRepository.save(music);
                log.info("새 노래 생성");
            }
            else log.info("이미 노래가 생성 되어 있습니다.");
        }
    }
}
