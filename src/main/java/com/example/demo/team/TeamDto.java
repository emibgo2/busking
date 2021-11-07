package com.example.demo.team;

import com.example.demo.teamBoard.TeamBoard;
import com.example.demo.user.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class TeamDto {

    private String teamName;
    private UserDto leader;
    private String teamIntroduce;
    private List<TeamBoard> notice;
    private Boolean onAir;
    private String onAirURL;
    private String teamProfileImg;
    private List<UserDto> userList;
}
