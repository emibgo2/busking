package com.example.demo.teamBoard;


import com.example.demo.team.Team;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

// ORM -> Java Object -> 테이블로 매핑해주는 기술
@Entity // User 클래스가 MySQL에 테이블이 생성이 된다
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder // 빌더 패턴!
public class TeamBoard {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Lob // 대용량 데이터
    private String content; // 섬머노트 라이브러리 <html> 태그가 섞여서 디자인이 됨

    private int count; // 조회수

    @ManyToOne(fetch = FetchType.EAGER) // Many = Many, User =One
    @JoinColumn(name = "team")
    private Team team; // DB 는 오브젝트를 저장할 수 없다. FK, 자바는 오브젝트를 저장할 수 있다.
    // User 데이터가 One 이기 때문에 foreign key 로 만들어짐

    @OneToMany(mappedBy = "board",fetch = FetchType.EAGER,cascade = CascadeType.REMOVE) // mappedBy가 적혀잇으면 연관관계의 주인이 아니다( FK가 아니다) , DB에 컬럼을 만들지 마세요
    @JsonIgnoreProperties({"board"})
    @OrderBy("id desc")
    private List<Reply> replys;

    @OneToMany(mappedBy = "board",fetch = FetchType.LAZY,cascade = CascadeType.REMOVE) // mappedBy가 적혀잇으면 연관관계의 주인이 아니다( FK가 아니다) , DB에 컬럼을 만들지 마세요
    @JsonIgnoreProperties({"board"})
    @OrderBy("id desc")
    private List<BoardLike> boardLikes;

    @CreationTimestamp
    private Timestamp createDate;

    @Column
    private int boardLike;

}
