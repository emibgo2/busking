package com.example.demo.user;


import com.example.demo.team.Team;
import com.example.demo.user.userDetail.UserDetail;
import com.example.demo.user.userDetail.UserDetailDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class User {

    // Test Data 생성을 위한 생성자
    public User(String Username, String password, String nickname, int birthday, Gender gender, Timestamp createDate) {
        this.username = Username;
        this.password = password;
        this.nickname = nickname;
        this.birthday = birthday;
        this.gender = gender;
        this.createDate = createDate;

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30, unique = true)
    @Email(message = "이메일 형식에 맞지 않습니다.")
    @NotBlank(message = "필수 값입니다.")
    private String username; // ID

    @Column(nullable = false, length = 100, unique = true)
    @NotBlank(message = "필수 값입니다.")
    private String password; // ID

    @Column(nullable = false, length = 20, unique = true)
    @NotBlank(message = "필수 값입니다.")
    private String nickname;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "teamId")
    private Team team;


    @Column(nullable = false)
    @NotNull(message = "필수 값입니다.")
    @Range(min = 1950, max = 2021)
    private int birthday;

    @Enumerated(EnumType.ORDINAL)
    @NotNull(message = "필수 값입니다.")
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private RoleType role;

    @CreationTimestamp
    private Timestamp createDate;

    @OneToOne(mappedBy = "user")
    @JsonIgnoreProperties({"user","userId"})
    private UserDetail userDetail;

    public UserDto userToDto(User user) {

        if (user.getUserDetail()==null) {
            return new UserDto(user.getNickname(), user.getBirthday(), user.getGender(),
                    new UserDetailDto(user.getNickname(), null, null));
        }
        return new UserDto(user.getNickname(), user.getBirthday(), user.getGender(),
                new UserDetailDto(user.getNickname(), user.getUserDetail().getProfileImgURL(), user.getUserDetail().getIntroduce()));
    }
}
