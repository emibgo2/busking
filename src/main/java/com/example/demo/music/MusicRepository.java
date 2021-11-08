package com.example.demo.music;

import com.example.demo.team.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MusicRepository extends JpaRepository<Music,Long> {

    Optional<Music> findFirstByOrderByIdDesc();

    Optional<Music> findByTitle(String title);

    Optional<List<Music>> findByTitleContains(String title);

    Optional<List<Music>> findByTitleContainsOrSingerContains(String title, String title2);

}
