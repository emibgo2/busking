package com.example.demo.room;

import com.example.demo.room.Rmusic.RMusic;
import com.example.demo.team.Team;
import com.example.demo.user.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Room(String roomName, Team onAirTeam, String introduce, String teamProfileImg) {
        this.roomName = roomName;
        this.onAirTeam = onAirTeam;
        this.introduce = introduce;
        this.teamProfileImg = teamProfileImg;
    }

    @NotBlank(message = "방 이름은 필수 입니다.")
    @Column(length = 40)
    private String roomName;

    @OneToOne
    private Team onAirTeam;

    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    // mappedBy가 적혀잇으면 연관관계의 주인이 아니다( FK가 아니다) , DB에 컬럼을 만들지 마세요
    @JsonIgnoreProperties({"room"})
    @OrderBy("id desc")
    private List<User> viewer;

    @Lob
    private String introduce;
    @Lob
    private String teamProfileImg;

    @Lob
    private String latIng;
//
//    @OneToMany(mappedBy = "chatRoom", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
//    // mappedBy가 적혀잇으면 연관관계의 주인이 아니다( FK가 아니다) , DB에 컬럼을 만들지 마세요
//    @JsonIgnoreProperties({"chatRoom"})
//    @OrderBy("id desc")
//    private List<Chat> chats;

    @OneToMany(mappedBy = "musicRoom", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    // mappedBy가 적혀잇으면 연관관계의 주인이 아니다( FK가 아니다) , DB에 컬럼을 만들지 마세요
    @JsonIgnoreProperties({"musicRoom"})
    @OrderBy("id asc")
    private List<RMusic> reservationMusic;

    public RoomDto roomToRoomDTO() {
        return new RoomDto(this.roomName, this.onAirTeam.getTeamName(), this.introduce,this.teamProfileImg, new User().userListToDtoList(this.viewer), this.reservationMusic, this.latIng);
    }

}
