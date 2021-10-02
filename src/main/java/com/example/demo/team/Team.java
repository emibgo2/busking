package com.example.demo.team;

import com.example.demo.teamBoard.TeamBoard;
import com.example.demo.user.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String teamName;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User leadder;

//  User를 넣을 것인가?  private MusicSession session;

    @Lob
    private String introDuce;

    @OneToMany(mappedBy = "team", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    // mappedBy가 적혀잇으면 연관관계의 주인이 아니다( FK가 아니다) , DB에 컬럼을 만들지 마세요
    @JsonIgnoreProperties({"team"})
    @OrderBy("id desc")
    private List<TeamBoard> notice;

    @Lob
    private String qrImg;

    @CreationTimestamp
    private Timestamp createDate;

    // 사용자 -> 팀 찜한 갯수 데이터 타입 어떻게 할 것인가?
//    private Long allLike;


}
