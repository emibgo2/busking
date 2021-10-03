package com.example.demo.user.userDetail;

import com.example.demo.user.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
public class UserDetail {
    // Test Data 생성을 위한 생성자
    public UserDetail(User user, String profileImgURL, String introduce) {
        this.user = user;
        this.profileImgURL = profileImgURL;
        this.introduce = introduce;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    @OneToOne
    @JoinColumn(name ="userId")
    @JsonIgnoreProperties("user")
    private User user;

    private String profileImgURL;

    @Lob
    private String introduce;
}
