package com.example.demo.music;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class MusicService {
    private final MusicRepository musicRepository;
    public List<Music> MusicList = new ArrayList<>();
    @Transactional(readOnly = true)
    public Music musicFindByTitle(String title){
        return musicRepository.findByTitle(title)
                .orElseGet(() -> {
                    return new Music();
                });
        // 해당 id값에 해당하는 Storage를 Return
    }

    @Transactional(readOnly = true)
    public List<Music> musicFindByTitleContain(String title){
        List<Music> music = musicRepository.findByTitleContains(title).orElseGet(() -> {
            return new ArrayList<>();
        });

        return music; // 정상
    }


    @Transactional(readOnly = true)
    public List<Music> musicFindByTitleAndSingerContain(String keyword){
        List<Music> music = musicRepository.findByTitleContainsOrSingerContains(keyword,keyword).orElseGet(() -> {
            return new ArrayList<>();
        });


        return music; // 정상
    }


    @Transactional
    public void save(Music music){
        musicRepository.save(music);
        // 해당 id값에 해당하는 Storage를 Return
    }


    @Transactional
    public int deleteTestDataAfter() {

        for (Long i =  musicRepository.count(); i > MusicList.size(); i--) {
            musicRepository.deleteById(i);
            log.info("{} 번 user가 삭제되었습니다.",i);
        }
        return HttpStatus.OK.value();


    }
    @PostConstruct
    public void init() {


        /**
         *  Music Test Data
         */
        MusicList.add(new Music("Jail", "Kanye West", "https://w.namu.la/s/3d07a08af0b3be7afeee94f2972ebc72241d2cc9f6ce06f5dc2cbff51d19f9184c69172d0e776cc70a0de6de927e86a409eb43843a4040657d0c8dcc2a59cdb9f932608fdb59d5cc041c55e0a2ec340f9ce77a9f9f4933e2250b1eedec7fdbfc"));
        MusicList.add(new Music("Jail Pt.2", "Kanye West", "https://w.namu.la/s/3d07a08af0b3be7afeee94f2972ebc72241d2cc9f6ce06f5dc2cbff51d19f9184c69172d0e776cc70a0de6de927e86a409eb43843a4040657d0c8dcc2a59cdb9f932608fdb59d5cc041c55e0a2ec340f9ce77a9f9f4933e2250b1eedec7fdbfc"));
        MusicList.add(new Music("Circles", "Post Malone", "http://image.yes24.com/goods/79640397/XL"));
        MusicList.add(new Music("TestTitle", "Jail", "https://w.namu.la/s/3d07a08af0b3be7afeee94f2972ebc72241d2cc9f6ce06f5dc2cbff51d19f9184c69172d0e776cc70a0de6de927e86a409eb43843a4040657d0c8dcc2a59cdb9f932608fdb59d5cc041c55e0a2ec340f9ce77a9f9f4933e2250b1eedec7fdbfc"));


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
