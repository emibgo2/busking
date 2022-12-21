package com.example.demo.music;

import com.example.demo.ResponseDto;
import com.example.demo.user.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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


    @GetMapping("/title/{title}/one") // 글자가 똑같아야함
    public ResponseEntity<Music> musicInfo(@PathVariable String title) {

        Music music = musicService.musicFindByTitle(title);
        if (music == null) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(music);
    }
    @GetMapping("/title/{title}")
    public ResponseEntity<List<Music>> musicTitleContain(@PathVariable String title) {
        List<Music> music = musicService.musicFindByTitleContain(title);
        if (music.isEmpty() ) {
            return new ResponseEntity(title + "는 없습니다", HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(music);
    }

    @GetMapping("/keyword/{keyword}")
    public ResponseEntity<List<Music>> musicKeywordContain(@PathVariable String keyword) {
        List<Music> music = musicService.musicFindByTitleAndSingerContain(keyword);
        if (music.isEmpty() ) {
            return new ResponseEntity(keyword + "에 해당되는 곡이 없습니다.", HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(music);
    }

    @GetMapping("/all")
    public ResponseEntity<List> musicAllInfo() {
        List<Music> allMusic = musicRepository.findAll();

        if (allMusic.isEmpty()) return new ResponseEntity<>(allMusic,HttpStatus.NO_CONTENT);
        return ResponseEntity.ok(allMusic);
    }

    @DeleteMapping("/all")
    public int delete() {
        return musicService.deleteTestDataAfter();
    }

    @PostMapping
    public void save(@Valid @RequestBody Music music) {
        musicService.save(music);
        log.info("Add for Music List ={}", music);
        // 유저 회원가입 메소드
    }


    // API 예외 메시지
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ResponseEntity illegalExHandler(IllegalArgumentException e) {
        log.error("[exceptionHandler] ex", e);
        return ResponseEntity.badRequest().build();
    }


}
