package com.example.demo.music;

import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Slf4j
public class MusicService {
    private final MusicRepository musicRepository;

    @Transactional(readOnly = true)
    public Music musicFindByTitle(String title){
        return musicRepository.findByTitle(title)
                .orElseGet(() -> {
                    return new Music();
                });
        // 해당 id값에 해당하는 Storage를 Return
    }

    @Transactional
    public void save(Music music){
        musicRepository.save(music);
        // 해당 id값에 해당하는 Storage를 Return
    }

}
