package com.example.demo.user.userDetail;

import com.example.demo.team.Team;
import lombok.*;

import javax.persistence.Lob;
import javax.validation.constraints.NotNull;


@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailDto {

    @NotNull(message = "필수 값입니다.")
    private String nickname;
    @Lob
    private String profileImgURL;
    @Lob
    private String introduce;
//  예정 private Team team;
}
