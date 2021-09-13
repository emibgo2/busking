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

    @PostConstruct
    public void init() {



    }
}
