package com.example.demo.user;

import com.example.demo.user.userDetail.UserDetail;
import com.example.demo.user.userDetail.UserDetailDto;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

@Getter @Setter
@AllArgsConstructor
public class UserDto {

    private String nickname;
    private int birthday;
    private Gender gender;
    private UserDetailDto userDetail;
}
