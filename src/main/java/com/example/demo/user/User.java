package com.example.demo.user;


import com.example.demo.RoleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class User {


    public User(String username, String password, String profileImgURL) {
        this.loginID = username;
        this.password = password;
        this.profileImgURL = profileImgURL;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30, unique = true)
    private String loginID; // ID

    @Column(nullable = false, length = 100, unique = true)
    private String password; // ID

    @Enumerated(EnumType.STRING)
    private RoleType role;

    private String profileImgURL;

}
