package com.example.demo.user.userDetail;

import com.example.demo.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIgnoreType;
import lombok.*;

import javax.persistence.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "user_detail")
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
    @JsonIgnore
    private Long id;

    @OneToOne
    @JoinColumn(name ="userId")
    private User user;

    @Lob
    private String profileImgURL;

    @Lob
    private String introduce;
}
