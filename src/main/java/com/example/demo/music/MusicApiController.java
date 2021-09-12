package com.example.demo.music;

import com.example.demo.ResponseDto;
import com.example.demo.user.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/music")
public class MusicApiController {

    public MusicRepository musicRepository;
    public MusicService musicService;


    @GetMapping("/{title}")
    public ResponseDto<Music> userInfo(@PathVariable String title) {
        return new ResponseDto<Music>(HttpStatus.OK.value(), musicService.musicFindByTitle(title));
    }

    @GetMapping("/all")
    public ResponseDto<List> musicAllInfo() {
        List<Music> all = musicRepository.findAll();
        for (int i = 0; i < all.size(); i++) {
            all.get(i).setId(i+1L);
        }
        return new ResponseDto<List>(HttpStatus.OK.value(), musicRepository.findAll());
    }

    @PostMapping
    public ResponseDto<Integer> save(@Valid @RequestBody Music music) {
        log.info("Add for Music List ={}", music);
        musicService.save(music);
        return new ResponseDto<Integer>(HttpStatus.CREATED.value(),1);
        // 유저 회원가입 메소드
    }

}
