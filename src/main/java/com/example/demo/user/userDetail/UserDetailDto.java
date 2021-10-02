package com.example.demo.user.userDetail;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Lob;
import javax.validation.constraints.NotNull;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailDto {

    @NotNull(message = "필수 값입니다.")
    private Long userId;

    @Lob
    private String profileImgURL;
    @Lob
    private String introDuce;
}
