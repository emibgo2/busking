package com.example.demo.team;

import com.example.demo.room.Room;
import com.example.demo.teamBoard.TeamBoard;
import com.example.demo.user.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Team {
    public Team(String teamName, User leader, String introduce, String teamProfileImg) {
        this.teamName = teamName;
        this.leader = leader;
        this.introduce = introduce;
        this.teamProfileImg = teamProfileImg;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "팀명은 필수 입니다.")
    @Column(length = 20,unique = true)
    private String teamName;

    @ManyToOne
    @JoinColumn(name = "leaderNickname")
    private User leader;

    @OneToOne
    @JoinColumn(name = "roomId")
    private Room onAirRoom;

    @OneToMany(mappedBy = "team", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    // mappedBy가 적혀잇으면 연관관계의 주인이 아니다( FK가 아니다) , DB에 컬럼을 만들지 마세요
    @JsonIgnoreProperties({"team"})
    @OrderBy("id desc")
    private List<User> userList;
//  User를 넣을 것인가?  private MusicSession session;

    @Lob
    private String introduce;

    @OneToMany(mappedBy = "team", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    // mappedBy가 적혀잇으면 연관관계의 주인이 아니다( FK가 아니다) , DB에 컬럼을 만들지 마세요
    @JsonIgnoreProperties({"team"})
    @OrderBy("id desc")
    private List<TeamBoard> notice;

    @Lob
    @Column
    private String qrImg;

    @Lob
    @Column
    private String teamProfileImg;

    @Column(columnDefinition = "boolean default false")
    private Boolean onAir =false;

    @Column
    private String onAirURL ;

    @CreationTimestamp
    private Timestamp createDate;

    // 사용자 -> 팀 찜한 갯수 데이터 타입 어떻게 할 것인가?
//    private Long allLike;


}
