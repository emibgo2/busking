package com.example.demo.team;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamSaveForm {
    @NotNull(message = "팀명은 필수!.")
    private String teamName;
    @NotNull(message = "팀장 이름은 필수!.")
    private String leaderName;
    private String introduce;
    private String teamProfileImg;
    private List<String> userList;
}
