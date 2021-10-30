package com.example.demo.room.Rmusic;

import com.example.demo.music.MusicRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/music")
public class RMusicApiController {

    public RMusicRepository RMusicRepository;
    public RMusicService RMusicService;
    public MusicRepository musicRepository;



    @PostConstruct
    public void init() {
//        List<Music> all = musicRepository.findAll();
//        List<RMusic> rMusicList = new ArrayList<>();
//        for (Music music : all) {
//            rMusicList.add(new RMusic())
//        }
//        RMusicRepository.saveAll();
    }


}
