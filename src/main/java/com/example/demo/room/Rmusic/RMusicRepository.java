package com.example.demo.room.Rmusic;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RMusicRepository extends JpaRepository<RMusic,Long> {
    Optional<RMusic> findByMusicRoomIdAndTitleAndSinger(Long roomId,String title, String singer);
    void deleteByMusicRoomIdAndTitleAndSingerAndUserNickname(Long roomId,String title, String singer, String userNickname);
    Optional<RMusic> findByTitle(String title);

    Optional<List<RMusic>> findByTitleContains(String title);

    Optional<List<RMusic>> findByTitleContainsOrSingerContains(String title, String title2);

}
