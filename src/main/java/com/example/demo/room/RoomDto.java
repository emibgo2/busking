package com.example.demo.room;

import com.example.demo.room.Rmusic.RMusic;
import com.example.demo.user.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Lob;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class RoomDto {
    @NotNull(message = "방명은 필수!.")
    private String roomName;
    @NotNull(message = "팀명은 필수!.")
    private String teamName;
    @Lob
    private String introduce;

    @Lob
    private String teamProfileImg;
    private List<UserDto> viewer;
    private List<RMusic> musics;


}
