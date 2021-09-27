package com.example.demo.user;



import com.example.demo.Gender;
import com.example.demo.RoleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class User {

    public User(String Username, String password, String nickName, int age, Gender gender, RoleType role, String profileImgURL, Timestamp createDate) {
        this.username = Username;
        this.password = password;
        this.nickName = nickName;
        this.age = age;
        this.gender = gender;
        this.role = role;
        this.profileImgURL = profileImgURL;
        this.createDate = createDate;

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30, unique = true)
    @Email(message = "이메일 형식에 맞지 않습니다.")
    @NotNull(message = "필수 값입니다.")
    private String username; // ID

    @Column(nullable = false, length = 100, unique = true)
    @NotNull(message = "필수 값입니다.")
    private String password; // ID

    @Column(nullable = false, length = 20, unique = true)
    @NotNull(message = "필수 값입니다.")
    private String nickName;

    @Column(nullable = false)
    @Range(min = 5, max = 100, message = "나이는 5~100세 사이로 설정해야 합니다.")
    @NotNull(message = "필수 값입니다.")
    private int age;

    @Enumerated(EnumType.ORDINAL)
    @NotNull(message = "필수 값입니다.")
    private Gender gender;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "필수 값입니다.")
    private RoleType role;

    @CreationTimestamp
    private Timestamp createDate;

    private String profileImgURL;

}
