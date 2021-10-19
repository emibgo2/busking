package com.example.demo.music;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Music {

    public Music(String title, String singer, String profileImgURL,String lyrics) {
        this.title = title;
        this.singer = singer;
        this.profileImgURL = profileImgURL;
        this.lyrics = lyrics;
    }
    public Music(String title, String singer, String profileImgURL) {
        this.title = title;
        this.singer = singer;
        this.profileImgURL = profileImgURL;

    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    @NotBlank(message = "필수값 입니다.")
    private String title; // 노래 제목

    @Column(nullable = false, length = 50)
    @NotBlank(message = "필수값 입니다.")
    private String singer; // 가수

    @Lob
    private String profileImgURL; // 앨범 커버

    @Lob
    private String lyrics; // 가사
}
