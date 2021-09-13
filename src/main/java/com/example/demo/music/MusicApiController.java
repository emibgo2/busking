package com.example.demo.music;

import com.example.demo.ResponseDto;
import com.example.demo.user.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/music")
public class MusicApiController {

    public MusicRepository musicRepository;
    public MusicService musicService;


    @GetMapping("/title/{title}/one")
    public ResponseDto<Music> musicInfo(@PathVariable String title) {
        return new ResponseDto<Music>(HttpStatus.OK.value(), musicService.musicFindByTitle(title));
    }
    @GetMapping("/title/{title}")
    public ResponseDto<Object> musicTitleContain(@PathVariable String title) {
        Object response = musicService.musicFindByTitleContain(title);
        if (response.equals(2) ) {
            return new ResponseDto<>(HttpStatus.NO_CONTENT.value(), title+"은 없습니다" );
        }
        return new ResponseDto<>(HttpStatus.OK.value(),response);
    }

    @GetMapping("/keyword/{keyword}")
    public ResponseDto<Object> musicKeywordContain(@PathVariable String keyword) {
        Object response = musicService.musicFindByTitleAndSingerContain(keyword);
        if (response.equals(2) ) {
            return new ResponseDto<>(HttpStatus.NO_CONTENT.value(), keyword+"은 없습니다" );
        }
        return new ResponseDto<>(HttpStatus.OK.value(),response);
    }
    @GetMapping("/all")
    public ResponseDto<List> musicAllInfo() {
        List<Music> all = musicRepository.findAll();
        for (int i = 0; i < all.size(); i++) {
            all.get(i).setId(i+1L);
        }
        return new ResponseDto<List>(HttpStatus.OK.value(), musicRepository.findAll());
    }

    @DeleteMapping("/all")
    public int delete() {
        return musicService.deleteTestDataAfter();
    }

    @PostMapping
    public ResponseDto<Integer> save(@Valid @RequestBody Music music) {
        log.info("Add for Music List ={}", music);
        musicService.save(music);
        return new ResponseDto<Integer>(HttpStatus.CREATED.value(),1);
        // 유저 회원가입 메소드
    }


}
