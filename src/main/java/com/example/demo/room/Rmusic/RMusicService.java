package com.example.demo.room.Rmusic;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class RMusicService {
    private final RMusicRepository RMusicRepository;
    public List<RMusic> RMusicList = new ArrayList<>();


    @Transactional(readOnly = true)
    public RMusic musicFindByTitle(String title){
        return RMusicRepository.findByTitle(title)
                .orElseGet(() -> {
                    return new RMusic();
                });
        // 해당 id값에 해당하는 Storage를 Return
    }

    @Transactional(readOnly = true)
    public List<RMusic> musicFindByTitleContain(String title){
        List<RMusic> RMusics = RMusicRepository.findByTitleContains(title).orElseGet(() -> {
            return new ArrayList<>();
        });

        return RMusics; // 정상
    }


    @Transactional(readOnly = true)
    public List<RMusic> musicFindByTitleAndSingerContain(String keyword){
        List<RMusic> RMusics = RMusicRepository.findByTitleContainsOrSingerContains(keyword,keyword).orElseGet(() -> {
            return new ArrayList<>();
        });


        return RMusics; // 정상
    }


    @Transactional
    public void save(RMusic rmusic){
        RMusicRepository.save(rmusic);
        // 해당 id값에 해당하는 Storage를 Return
    }


    @Transactional
    public int deleteTestDataAfter() {

        for (Long i = RMusicRepository.count(); i > RMusicList.size(); i--) {
            RMusicRepository.deleteById(i);
            log.info("{} 번 user가 삭제되었습니다.",i);
        }
        return HttpStatus.OK.value();


    }

}
