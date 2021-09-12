package com.example.demo.music;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MusicRepository extends JpaRepository<Music,Long> {

    Optional<Music> findByTitle(String title);

}
